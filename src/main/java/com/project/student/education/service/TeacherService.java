package com.project.student.education.service;

import com.project.student.education.DTO.TeacherDTO;
import com.project.student.education.entity.Assignment;
import com.project.student.education.entity.ClassSection;
import com.project.student.education.entity.Subject;
import com.project.student.education.entity.Teacher;
import com.project.student.education.repository.AssignmentRepository;
import com.project.student.education.repository.ClassSectionRepository;
import com.project.student.education.repository.SubjectRepository;
import com.project.student.education.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;
    private final ClassSectionRepository classSectionRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubjectRepository subjectRepository;

    public TeacherDTO addTeacher(TeacherDTO dto) {
        if (teacherRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        long count = teacherRepository.count() + 1;
        String newId = "TCH" + String.format("%03d", count);

        Teacher teacher = modelMapper.map(dto, Teacher.class);
        teacher.setTeacherId(newId);
        teacherRepository.save(teacher);

        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
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
        modelMapper.map(dto, teacher);
        teacher.setTeacherId(teacherId);
        teacherRepository.save(teacher);
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public String deleteTeacher(String teacherId) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // 1. Remove Teacher from ClassSection
        List<ClassSection> sections = classSectionRepository.findByClassTeacher_TeacherId(teacherId);
        for (ClassSection sec : sections) {
            sec.setClassTeacher(null);
        }
        classSectionRepository.saveAll(sections);


        List<Assignment> assignments = assignmentRepository.findByTeacher_TeacherId(teacherId);
        for (Assignment a : assignments) {
            a.setTeacher(null);
            a.setCreatedBy(null);
        }
        assignmentRepository.saveAll(assignments);

        teacherRepository.deleteById(teacherId);

        return "Teacher deleted successfully";
    }


}
