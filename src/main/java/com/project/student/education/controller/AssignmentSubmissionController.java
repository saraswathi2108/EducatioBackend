package com.project.student.education.controller;

import com.project.student.education.DTO.AssignmentSubmissionDTO;
import com.project.student.education.service.AssignmentSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment-submissions")
@RequiredArgsConstructor
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService submissionService;

    @PostMapping("/{assignmentId}/{subjectId}")
    public ResponseEntity<String> submitAssignment(
            @PathVariable String assignmentId,
            @PathVariable String subjectId,
            @RequestBody AssignmentSubmissionDTO dto) {
        return new ResponseEntity<>(submissionService.submitAssignment(assignmentId, subjectId, dto), HttpStatus.CREATED);
    }


    @PutMapping("/{assignmentId}/{subjectId}/{submissionNumber}")
    public ResponseEntity<String> reviewSubmission(
            @PathVariable String assignmentId,
            @PathVariable String subjectId,
            @PathVariable Long submissionNumber,
            @RequestBody AssignmentSubmissionDTO dto) {
        return ResponseEntity.ok(submissionService.reviewSubmission(assignmentId, subjectId, submissionNumber, dto));
    }


    @GetMapping("/{assignmentId}/{subjectId}/all")
    public ResponseEntity<List<AssignmentSubmissionDTO>> getAllSubmissions(
            @PathVariable String assignmentId,
            @PathVariable String subjectId) {
        return ResponseEntity.ok(submissionService.getSubmissions(assignmentId, subjectId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AssignmentSubmissionDTO>> getSubmissionsByStudent(
            @PathVariable String studentId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByStudent(studentId));
    }
}
