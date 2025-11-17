package com.project.student.education.service;

import com.project.student.education.DTO.StudentDTO;
import com.project.student.education.DTO.SubjectDTO;
import com.project.student.education.entity.Admission;
import com.project.student.education.entity.ClassSection;
import com.project.student.education.entity.Student;
import com.project.student.education.entity.Subject;
import com.project.student.education.repository.AdmissionRepository;
import com.project.student.education.repository.ClassSectionRepository;
import com.project.student.education.repository.StudentRepository;
import com.project.student.education.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private ClassSectionRepository classSectionRepository;

    @Autowired
    private SubjectRepository subjectRepository;


    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        return convertToDTO(student);
    }

    @Transactional
    public StudentDTO updateStudent(String studentId, StudentDTO dto) {
        Student existing = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        existing.setFullName(dto.getFullName());
        existing.setGender(dto.getGender());
        existing.setEmail(dto.getEmail());
        existing.setContactNumber(dto.getContactNumber());
        existing.setAcademicYear(dto.getAcademicYear());
        existing.setRollNumber(dto.getRollNumber());
        existing.setActive(dto.getActive());

        if (dto.getAdmissionNumber() != null) {
            Admission admission = admissionRepository.findByAdmissionNumber(dto.getAdmissionNumber())
                    .orElseThrow(() -> new RuntimeException("Admission not found: " + dto.getAdmissionNumber()));
            existing.setAdmission(admission);
            admission.setStudent(existing);
        }

        if (dto.getClassSectionId() != null) {
            ClassSection section = classSectionRepository.findById(dto.getClassSectionId())
                    .orElseThrow(() -> new RuntimeException("Class section not found: " + dto.getClassSectionId()));
            existing.setClassSection(section);
        }

        Student updated = studentRepository.save(existing);
        return convertToDTO(updated);
    }

    @Transactional
    public StudentDTO deleteStudent(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        if (student.getAdmission() != null) {
            Admission admission = student.getAdmission();
            admission.setStudent(null);
            admissionRepository.save(admission);
        }

        studentRepository.delete(student);
        return convertToDTO(student);
    }


    @Transactional
    public StudentDTO transferStudent(String studentId, String targetClassSectionId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ClassSection newSection = classSectionRepository.findById(targetClassSectionId)
                .orElseThrow(() -> new RuntimeException("Target class section not found"));

        student.setClassSection(newSection);
        student.setAcademicYear(newSection.getAcademicYear());
        studentRepository.save(student);

        StudentDTO dto = convertToDTO(student);
        dto.setClassSectionId(newSection.getClassSectionId());
        dto.setGrade(newSection.getClassName());
        dto.setSection(newSection.getSection());
        return dto;
    }


//    public List<SubjectDTO> getSubjectsForStudent(String studentId) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//        if (student.getClassSection() == null) {
//            throw new RuntimeException("Student not assigned to a class");
//        }
//
////        List<Subject> subjects = subjectRepository.findByClassSection_ClassSectionId(
////                student.getClassSection().getClassSectionId()
////        );
//
//        return subjects.stream().map(this::convertSubjectToDTO).collect(Collectors.toList());
//    }


    private StudentDTO convertToDTO(Student student) {
        if (student == null) return null;

        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getStudentId());
        dto.setFullName(student.getFullName());
        dto.setGender(student.getGender());
        dto.setRollNumber(student.getRollNumber());
        dto.setAcademicYear(student.getAcademicYear());
        dto.setContactNumber(student.getContactNumber());
        dto.setEmail(student.getEmail());
        dto.setActive(student.getActive());
        dto.setProfileImageUrl(student.getProfileImageUrl());
        dto.setFatherName(student.getFatherName());
        dto.setMotherName(student.getMotherName());
        dto.setGuardianName(student.getGuardianName());

        if (student.getAdmission() != null) {
            dto.setAdmissionNumber(student.getAdmission().getAdmissionNumber());
        }

        if (student.getClassSection() != null) {
            dto.setClassSectionId(student.getClassSection().getClassSectionId());
            dto.setGrade(student.getClassSection().getClassName());
            dto.setSection(student.getClassSection().getSection());
        }

        return dto;
    }


    private SubjectDTO convertSubjectToDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setSubjectId(subject.getSubjectId());
        dto.setSubjectName(subject.getSubjectName());

        return dto;
    }
}
