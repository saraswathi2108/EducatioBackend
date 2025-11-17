package com.project.student.education.service;

import com.project.student.education.DTO.*;
import com.project.student.education.entity.ExamRecord;
import com.project.student.education.enums.RecordStatus;
import com.project.student.education.repository.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamSchedulingService {

    private final ModelMapper mapper;
    private final ExamMasterRepository examRepo;
    private final ClassSectionRepository classRepo;
    private final SubjectRepository subjectRepo;
    private final StudentRepository studentRepo;
    private final ExamRecordRepository recordRepo;
    private final ClassSubjectMappingRepository classSubjectRepo;

    @Transactional
    public ExamRecordDTO scheduleOne(ExamRecordDTO dto) {

        validateScheduleInput(dto);

        if (recordRepo.existsByExamIdAndClassSectionIdAndSubjectIdAndStudentId(
                dto.getExamId(), dto.getClassSectionId(), dto.getSubjectId(), dto.getStudentId())) {
            throw new RuntimeException("Exam already scheduled for this student & subject.");
        }

        ExamRecord record = mapper.map(dto, ExamRecord.class);
        record.setStatus(RecordStatus.SCHEDULED.name());
        record.setCreatedAt(LocalDateTime.now());
        record.setEnteredBy(getCurrentUser());

        ExamRecord saved = recordRepo.save(record);
        return mapper.map(saved, ExamRecordDTO.class);
    }


    @Transactional
    public List<ExamRecordDTO> scheduleBulk(BulkScheduleRequest req) {

        if (!examRepo.existsById(req.getExamId()))
            throw new EntityNotFoundException("Exam not found");
        if (!classRepo.existsById(req.getClassSectionId()))
            throw new EntityNotFoundException("Class not found");
        if (!subjectRepo.existsById(req.getSubjectId()))
            throw new EntityNotFoundException("Subject not found");

        List<String> studentIds = req.getStudentIds();
        if (studentIds == null || studentIds.isEmpty()) {
            studentIds = studentRepo.findStudentIdsByClassSectionId(req.getClassSectionId());
        }

        List<ExamRecordDTO> output = new ArrayList<>();

        for (String studentId : studentIds) {

            if (recordRepo.existsByExamIdAndClassSectionIdAndSubjectIdAndStudentId(
                    req.getExamId(), req.getClassSectionId(), req.getSubjectId(), studentId)) {
                continue;
            }

            ExamRecord record = ExamRecord.builder()
                    .examId(req.getExamId())
                    .classSectionId(req.getClassSectionId())
                    .subjectId(req.getSubjectId())
                    .studentId(studentId)
                    .examDate(req.getExamDate())
                    .startTime(req.getStartTime())
                    .endTime(req.getEndTime())
                    .roomNumber(req.getRoomNumber())
                    .invigilatorId(req.getInvigilatorId())
                    .maxMarks(req.getMaxMarks())
                    .passMarks(req.getPassMarks())
                    .status(RecordStatus.SCHEDULED.name())
                    .createdAt(LocalDateTime.now())
                    .enteredBy(getCurrentUser())
                    .build();

            ExamRecord saved = recordRepo.save(record);
            output.add(mapper.map(saved, ExamRecordDTO.class));
        }

        return output;
    }


    public List<ExamRecordDTO> getTimetable(String examId, String classSectionId) {
        return recordRepo.findByExamIdAndClassSectionId(examId, classSectionId)
                .stream()
                .map(r -> mapper.map(r, ExamRecordDTO.class))
                .collect(Collectors.toList());
    }

    private void validateScheduleInput(ExamRecordDTO dto) {
        if (!examRepo.existsById(dto.getExamId()))
            throw new EntityNotFoundException("Exam not found");

        if (!classRepo.existsById(dto.getClassSectionId()))
            throw new EntityNotFoundException("Class not found");

        if (!subjectRepo.existsById(dto.getSubjectId()))
            throw new EntityNotFoundException("Subject not found");

        if (!studentRepo.existsById(dto.getStudentId()))
            throw new EntityNotFoundException("Student not found");
    }

    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "SYSTEM";
    }

    public SubjectMarksEntryRequest enterMarks(SubjectMarksEntryRequest request, String subjectId) {

        subjectRepo.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found: " + subjectId));

                classSubjectRepo
                        .findByClassSection_ClassSectionIdAndSubject_SubjectId(request.getClassSectionId(), subjectId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Subject " + subjectId + " does NOT belong to class section " + request.getClassSectionId()
                        ));

        request.setSubjectId(subjectId);

        for (SubjectWiseMarksEntryDTO entry : request.getEntries()) {

            ExamRecord record = recordRepo.findByExamIdAndStudentIdAndSubjectId(
                    request.getExamId(),
                    entry.getStudentId(),
                    subjectId
            ).orElseThrow(() -> new EntityNotFoundException(
                    "Exam record not found for student " + entry.getStudentId()
            ));

            record.setMarksObtained(entry.getMarksObtained());
            record.setAttendanceStatus(entry.getAttendanceStatus());
            record.setRemarks(entry.getRemarks());
            record.setStatus(RecordStatus.MARKS_ENTERED.name());
            record.setUpdatedAt(LocalDateTime.now());
            record.setEnteredBy(getCurrentUser());

            recordRepo.save(record);
        }

        return request;
    }
}
