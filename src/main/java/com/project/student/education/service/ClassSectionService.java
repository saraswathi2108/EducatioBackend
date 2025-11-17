package com.project.student.education.service;

import com.project.student.education.DTO.ClassSectionDTO;
import com.project.student.education.DTO.StudentDTO;
import com.project.student.education.entity.ClassSection;
import com.project.student.education.entity.IdGenerator;
import com.project.student.education.entity.Student;
import com.project.student.education.entity.Teacher;
import com.project.student.education.repository.ClassSectionRepository;
import com.project.student.education.repository.ClassSubjectMappingRepository;
import com.project.student.education.repository.StudentRepository;
import com.project.student.education.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassSectionService {

    private final ClassSectionRepository classSectionRepository;
    private final TeacherRepository teacherRepository;
    private final IdGenerator idGenerator;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final ClassSubjectMappingRepository classSubjectMappingRepository;

    public ClassSectionDTO createClassSection(ClassSectionDTO dto) {

        classSectionRepository.findByClassNameAndAcademicYear(
                dto.getClassName(), dto.getAcademicYear()
        ).ifPresent(existing -> {
            throw new RuntimeException("Class section already exists for this academic year!");
        });

        String id = idGenerator.generateId("CLS");

        Teacher teacher = null;
        if (dto.getClassTeacherId() != null) {
            teacher = teacherRepository.findById(dto.getClassTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + dto.getClassTeacherId()));
        }

        ClassSection classSection = ClassSection.builder()
                .classSectionId(id)
                .className(dto.getClassName())
                .section(dto.getSection())
                .academicYear(dto.getAcademicYear())
                .classTeacher(teacher)
                .capacity(dto.getCapacity())
                .currentStrength(dto.getCurrentStrength())
                .build();

        ClassSection saved = classSectionRepository.save(classSection);

        return mapToDTO(saved);
    }

    public List<ClassSectionDTO> getAllClassSections() {
        return classSectionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ClassSectionDTO getClassSection(String className, String section, String academicYear) {
        ClassSection found = classSectionRepository
                .findByClassNameAndAcademicYear(className, academicYear)
                .orElseThrow(() -> new RuntimeException("Class section not found!"));
        return mapToDTO(found);
    }

    public List<StudentDTO> getStudentsByClassSection(String classSectionId) {
        List<Student> students = studentRepository.findByClassSection_ClassSectionId(classSectionId);

        if (students.isEmpty()) {
            throw new RuntimeException("No students found for class section: " + classSectionId);
        }

        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .toList();
    }

    public ClassSectionDTO assignTeacher(String classSectionId, String teacherId, String teacherName) {
        ClassSection classSection = classSectionRepository.findById(classSectionId)
                .orElseThrow(() -> new RuntimeException("Class section not found"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + teacherId));

        classSection.setClassTeacher(teacher);

        ClassSection updated = classSectionRepository.save(classSection);
        return mapToDTO(updated);
    }

    @Transactional
    public ClassSectionDTO updateClassSection(String id, ClassSectionDTO dto) {
        ClassSection existing = classSectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class section not found"));

        existing.setClassName(dto.getClassName());
        existing.setSection(dto.getSection());
        existing.setAcademicYear(dto.getAcademicYear());
        existing.setCapacity(dto.getCapacity());
        existing.setCurrentStrength(dto.getCurrentStrength());

        if (dto.getClassTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getClassTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + dto.getClassTeacherId()));
            existing.setClassTeacher(teacher);
        } else {
            existing.setClassTeacher(null);
        }

        ClassSection saved = classSectionRepository.save(existing);
        return mapToDTO(saved);
    }

    private ClassSectionDTO mapToDTO(ClassSection section) {
        ClassSectionDTO dto = modelMapper.map(section, ClassSectionDTO.class);

        if (section.getClassTeacher() != null) {
            dto.setClassTeacherId(section.getClassTeacher().getTeacherId());
            dto.setClassTeacherName(section.getClassTeacher().getTeacherName());
        }

        return dto;
    }

    public ClassSectionDTO assignStudentToClassSection(String classSectionId, String studentId) {

        ClassSection classSection = classSectionRepository.findById(classSectionId)
                .orElseThrow(() -> new RuntimeException("Class section not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setClassSection(classSection);
        student.setGrade(classSection.getClassName());
        student.setSection(classSection.getSection());
        studentRepository.save(student);

        int current = (classSection.getCurrentStrength() == null) ? 0 : classSection.getCurrentStrength();
        classSection.setCurrentStrength(current + 1);

        classSectionRepository.save(classSection);

        return mapToDTO(classSection);
    }


    @Transactional
    public ClassSectionDTO deleteClassSection(String classSectionId) {
        ClassSection classSection=classSectionRepository.findById(classSectionId)
                .orElseThrow(() -> new RuntimeException("Class section not found"));
        classSubjectMappingRepository.deleteByClassSection(classSection);
        classSectionRepository.delete(classSection);
        return mapToDTO(classSection);
    }
}
