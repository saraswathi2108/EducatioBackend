package com.project.student.education.repository;

import com.project.student.education.entity.ExamRecord;
import com.project.student.education.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {

    List<ExamRecord> findByExamId(String examId);

    List<ExamRecord> findByStudentId(String studentId);

    List<ExamRecord> findByClassSectionIdAndExamId(String classSectionId, String examId);

    List<ExamRecord> findBySubjectIdAndExamId(String subjectId, String examId);


    boolean existsByExamIdAndClassSectionIdAndSubjectIdAndStudentId(String examId, String classSectionId, String subjectId, String studentId);

    Collection<Object> findByExamIdAndClassSectionId(String examId, String classSectionId);

    Optional<ExamRecord> findByExamIdAndStudentIdAndSubjectId(String examId, String studentId, String subjectId);
}
