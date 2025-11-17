package com.project.student.education.service;

import com.project.student.education.DTO.AdmissionDTO;
import com.project.student.education.DTO.StudentDTO;
import com.project.student.education.entity.*;
import com.project.student.education.enums.AdmissionStatus;
import com.project.student.education.enums.Role;
import com.project.student.education.repository.AdmissionRepository;
import com.project.student.education.repository.ClassSectionRepository;
import com.project.student.education.repository.StudentRepository;
import com.project.student.education.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final IdGenerator idGenerator;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final ClassSectionRepository classSectionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AdmissionDTO submitAdmission(Admission admission) {
        String admissionNumber = idGenerator.generateId("ADM");
        admission.setAdmissionNumber(admissionNumber);



        admission.setStatus(AdmissionStatus.PENDING);
        admission.setAdmissionDate(LocalDate.now());
        admission.setCreatedAt(LocalDate.now().atStartOfDay());

        Admission savedAdmission = admissionRepository.save(admission);

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(savedAdmission, AdmissionDTO.class);
    }


    @Transactional
    public StudentDTO approveAdmission(String admissionNumber, String approvedBy, String academicYear) {

        Admission admission = admissionRepository.findById(admissionNumber)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        if (admission.getStatus() == AdmissionStatus.APPROVED) {
            return mapToStudentDTO(admission.getStudent(), admissionNumber);
        }

        admission.setStatus(AdmissionStatus.APPROVED);
        admission.setApprovedBy(approvedBy != null ? approvedBy : "admin");
        admission.setApprovedDate(LocalDate.now());

        String studentId = idGenerator.generateId("STU");
        String year = academicYear != null ? academicYear : admission.getAcademicYear();

        Student student = Student.builder()
                .studentId(studentId)
                .fullName(admission.getApplicantName())
                .dateOfBirth(admission.getDateOfBirth())
                .gender(admission.getGender())
                .bloodGroup(admission.getBloodGroup())
                .nationality(admission.getNationality())
                .religion(admission.getReligion())
                .category(admission.getCategory())
                .aadhaarNumber(admission.getAadhaarNumber())

                .grade(admission.getGradeApplied())
                .section(null)
                .classSection(null)
                .rollNumber(null)

                .academicYear(year)
                .joiningDate(LocalDate.now())

                .address(admission.getAddress())
                .city(admission.getCity())
                .state(admission.getState())
                .pincode(admission.getPincode())

                .fatherName(admission.getFatherName())
                .fatherContact(admission.getFatherContact())
                .motherName(admission.getMotherName())
                .motherContact(admission.getMotherContact())
                .guardianName(admission.getGuardianName())
                .guardianContact(admission.getGuardianContact())

                .emergencyContactName(admission.getEmergencyContactName())
                .emergencyContactNumber(admission.getEmergencyContactNumber())

                .profileImageUrl(admission.getPhotoUrl())
                .active(true)
                .build();

        // Create user account
        String rawPassword = generateDefaultPassword(studentId);

        User user = User.builder()
                .username(studentId)
                .password(passwordEncoder.encode(rawPassword))
                .role(Role.ROLE_STUDENT)
                .build();

        userRepository.save(user);
        student.setUser(user);

        studentRepository.save(student);

        admission.setStudent(student);
        admissionRepository.save(admission);

        StudentDTO dto = mapToStudentDTO(student, admissionNumber);
        dto.setGeneratedPassword(rawPassword);

        return dto;
    }



    private String generateDisplayRollNumber(String grade, String section) {
        return grade + section + "-" + (int)(Math.random() * 900 + 100);
    }

    private String generateDefaultPassword(String studentId) {
        return "Stu@" + studentId.substring(studentId.length() - 4);
    }

    private StudentDTO mapToStudentDTO(Student student, String admissionNumber) {
        StudentDTO dto = StudentDTO.builder()
                .studentId(student.getStudentId())
                .admissionNumber(admissionNumber)
                .fullName(student.getFullName())
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender())
                .bloodGroup(student.getBloodGroup())
                .nationality(student.getNationality())
                .religion(student.getReligion())
                .category(student.getCategory())
                .aadhaarNumber(student.getAadhaarNumber())
                .academicYear(student.getAcademicYear())
                .address(student.getAddress())
                .city(student.getCity())
                .state(student.getState())
                .pincode(student.getPincode())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .guardianName(student.getGuardianName())
                .emergencyContactName(student.getEmergencyContactName())
                .emergencyContactNumber(student.getEmergencyContactNumber())
                .profileImageUrl(student.getProfileImageUrl())
                .active(student.getActive())
                .build();

        if (student.getClassSection() != null) {
            dto.setClassSectionId(student.getClassSection().getClassSectionId());
            dto.setGrade(student.getClassSection().getClassName());
            dto.setSection(student.getClassSection().getSection());
            dto.setAcademicYear(student.getClassSection().getAcademicYear());
        }

        return dto;
    }


    public List<AdmissionDTO> getAllAdmissions() {
        return admissionRepository.findAll()
                .stream()
                .map(admission -> modelMapper.map(admission, AdmissionDTO.class))
                .toList();
    }


    public AdmissionDTO rejectAdmission(String admissionNumber, String reason) {
        Admission admission = admissionRepository.findById(admissionNumber)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        if (admission.getStatus() == AdmissionStatus.APPROVED) {
            throw new RuntimeException("Cannot reject an already approved admission");
        }

        admission.setStatus(AdmissionStatus.REJECTED);
        admission.setRemarks(reason != null ? reason : "No reason provided");
        admissionRepository.save(admission);

        return modelMapper.map(admission, AdmissionDTO.class);
    }


    public AdmissionDTO deleteAdmission(String admissionNumber) {
        Admission admission = admissionRepository.findById(admissionNumber)
                .orElseThrow(() -> new RuntimeException("Admission not found"));
        if (admission.getStatus() == AdmissionStatus.APPROVED) {
            throw new RuntimeException("Cannot delete an approved admission record");
        }

        admissionRepository.delete(admission);
        return modelMapper.map(admission, AdmissionDTO.class);
    }

    public List<AdmissionDTO> getAdmissionsByStatus(String status) {
        return admissionRepository.findByStatus(AdmissionStatus.valueOf(status.toUpperCase()))
                .stream()
                .map(a -> modelMapper.map(a, AdmissionDTO.class))
                .toList();
    }


}
