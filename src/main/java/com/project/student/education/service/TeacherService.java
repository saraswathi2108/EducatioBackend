package com.project.student.education.service;

import com.project.student.education.DTO.ClassSectionMiniDTO;
import com.project.student.education.DTO.TeacherDTO;
import com.project.student.education.entity.ClassSection;
import com.project.student.education.entity.IdGenerator;
import com.project.student.education.entity.Teacher;
import com.project.student.education.entity.User;
import com.project.student.education.enums.Role;
import com.project.student.education.repository.ClassSectionRepository;
import com.project.student.education.repository.TeacherRepository;
import com.project.student.education.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ClassSectionRepository classSectionRepository;

    public TeacherDTO addTeacher(TeacherDTO dto) {
        if (teacherRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String teacherId = idGenerator.generateId("TCH");
        String rawPassword = generateDefaultPassword(teacherId);

        User user = User.builder()
                .username(teacherId)
                .password(passwordEncoder.encode(rawPassword))
                .role(Role.ROLE_TEACHER)
                .build();

        userRepository.save(user);

        Teacher teacher = Teacher.builder()
                .teacherId(teacherId)
                .teacherName(dto.getTeacherName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .qualification(dto.getQualification())
                .gender(dto.getGender())
                .experience(dto.getExperience())
                .address(dto.getAddress())
                .user(user)
                .build();

        teacherRepository.save(teacher);

        TeacherDTO response = modelMapper.map(teacher, TeacherDTO.class);
        response.setPassword(rawPassword);

        return response;
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(t -> modelMapper.map(t, TeacherDTO.class))
                .collect(Collectors.toList());
    }

    public TeacherDTO getTeacherById(String teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public TeacherDTO updateTeacher(String teacherId, TeacherDTO dto) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setTeacherName(dto.getTeacherName());
        teacher.setEmail(dto.getEmail());
        teacher.setPhone(dto.getPhone());
        teacher.setQualification(dto.getQualification());
        teacher.setGender(dto.getGender());
        teacher.setExperience(dto.getExperience());
        teacher.setAddress(dto.getAddress());

        teacherRepository.save(teacher);
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public String deleteTeacher(String teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacherRepository.delete(teacher);
        userRepository.delete(teacher.getUser());

        return "Teacher deleted successfully";
    }

    private String generateDefaultPassword(String teacherId) {
        return "Tch@" + teacherId.substring(teacherId.length() - 4);
    }

    public String assignTeacher(String teacherId, String classSectionId) {
        ClassSection classSection = classSectionRepository.findById(classSectionId)
                .orElseThrow(() -> new RuntimeException("Class section not found"));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        classSection.setClassTeacher(teacher);
        classSectionRepository.save(classSection);
        return "Teacher  " + teacherId + "  assigned to class  " + classSectionId + "  successfully";
    }

    public String updateClassTeacher(String classSectionId, String teacherId) {

        ClassSection section = classSectionRepository.findById(classSectionId)
                .orElseThrow(() -> new RuntimeException("Class section not found"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        section.setClassTeacher(teacher);
        classSectionRepository.save(section);

        return "Class teacher updated for class section " + classSectionId +
                " to teacher " + teacherId;
    }

    public List<ClassSectionMiniDTO> getClassesHandledByTeacher(String teacherId) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherId));

        List<ClassSection> sections = classSectionRepository
                .findByClassTeacher_TeacherId(teacherId);

        return sections.stream().map(sec -> ClassSectionMiniDTO.builder()
                .classSectionId(sec.getClassSectionId())
                .className(sec.getClassName())
                .sectionName(sec.getSection())
                .academicYear(sec.getAcademicYear())
                .build()
        ).toList();
    }

    public Long getTeacherCount() {
        return teacherRepository.countTeachers();
    }



}
