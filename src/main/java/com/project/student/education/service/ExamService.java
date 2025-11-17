package com.project.student.education.service;

import com.project.student.education.DTO.ExamMasterDTO;
import com.project.student.education.DTO.ExamRecordDTO;
import com.project.student.education.DTO.UpdateExamStatusRequest;
import com.project.student.education.entity.ExamMaster;
import com.project.student.education.entity.IdGenerator;
import com.project.student.education.enums.ExamStatus;
import com.project.student.education.repository.ClassSectionRepository;
import com.project.student.education.repository.ExamMasterRepository;
import com.project.student.education.repository.ExamRecordRepository;
import com.project.student.education.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final IdGenerator idGenerator;
    private final ModelMapper modelMapper;
    private final ExamMasterRepository examRepo;
    private final ExamRecordRepository examRecordRepo;

    private final ClassSectionRepository classSectionRepo;
    private final SubjectRepository subjectRepo;

    public ExamMasterDTO createExam(ExamMasterDTO examMasterDTO) {
        if (examRepo.existsByExamNameAndAcademicYear((examMasterDTO.getExamName()), examMasterDTO.getAcademicYear())) {
            throw new RuntimeException("Exam already exists for this academic year");
        }        String examId = idGenerator.generateId("EXM");
        String username = getCurrentUser();

        ExamMaster exam = modelMapper.map(examMasterDTO, ExamMaster.class);
        exam.setExamId(examId);
        exam.setStatus(ExamStatus.CREATED);
        exam.setCreatedBy(username);

        return modelMapper.map(examRepo.save(exam), ExamMasterDTO.class);
    }

    public List<ExamMasterDTO> getAllExams() {
        return examRepo.findAll()
                .stream()
                .map(exam -> modelMapper.map(exam, ExamMasterDTO.class))
                .collect(Collectors.toList());
    }

    public ExamMasterDTO getExamById(String examId) {
        ExamMaster exam = examRepo.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam with id " + examId + " not found"));
        return modelMapper.map(exam, ExamMasterDTO.class);
    }

    public ExamMasterDTO updateExamStatus(String examId, UpdateExamStatusRequest request) {
        ExamMaster exam = examRepo.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        exam.setStatus(request.getStatus());
        exam.setUpdatedBy(getCurrentUser());
        exam.setUpdatedAt(LocalDateTime.now());

        return modelMapper.map(examRepo.save(exam), ExamMasterDTO.class);
    }



    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "SYSTEM";
    }


}
