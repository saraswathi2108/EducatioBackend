package com.project.student.education.repository;

import com.project.student.education.config.AssignmentSubmissionId;
import com.project.student.education.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, AssignmentSubmissionId> {

    @Query("SELECT MAX(a.id.submissionNumber) FROM AssignmentSubmission a " +
            "WHERE a.id.assignmentId.assignmentId = :assignmentId " +
            "AND a.id.assignmentId.subjectId = :subjectId")
    Long findMaxSubmissionNumber(String assignmentId, String subjectId);

    List<AssignmentSubmission> findByStudentId(String studentId);

}
