package com.project.student.education.controller;

import com.project.student.education.DTO.AssignmentDTO;
import com.project.student.education.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/assignment/{teacherId}/{subjectId}/{classSectionId}")
    public ResponseEntity<AssignmentDTO> createAssignment(@PathVariable String teacherId,
                                                          @PathVariable String subjectId,
                                                          @PathVariable String classSectionId,
                                                          @RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO response = assignmentService.createAssignment(teacherId, subjectId, classSectionId, assignmentDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/assignment/{subjectId}/{assignmentId}")
    public ResponseEntity<AssignmentDTO> updateAssignment(
            @PathVariable String subjectId, @PathVariable String assignmentId,
            @RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO assignmentDTO1 = assignmentService.updateAssignment(subjectId, assignmentId, assignmentDTO);
        return new ResponseEntity<>(assignmentDTO1, HttpStatus.OK);
    }

    @GetMapping("/assignment/{subjectId}/{assignmentId}")
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable String subjectId, @PathVariable String assignmentId) {
        AssignmentDTO assignmentDTO = assignmentService.getAssignment(subjectId, assignmentId);
        return new ResponseEntity<>(assignmentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/assignment/{subjectId}/{assignmentId}")
    public ResponseEntity<AssignmentDTO> deleteAssignment(@PathVariable String subjectId, @PathVariable String assignmentId) {
        AssignmentDTO assignmentDTO = assignmentService.deleteAssignment(subjectId, assignmentId);
        return new ResponseEntity<>(assignmentDTO, HttpStatus.OK);
    }

    @GetMapping("/assignments/teacher/{teacherId}")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments(@PathVariable String teacherId) {
        List<AssignmentDTO> assignmentDTO = assignmentService.getAllByTeacher(teacherId);
        return new ResponseEntity<>(assignmentDTO, HttpStatus.OK);
    }

    @GetMapping("/assignments/subject/{subjectId}")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignmentsBySubject(@PathVariable String subjectId) {
        List<AssignmentDTO> assignmentDTOS = assignmentService.getAllAssignmentsBySubject(subjectId);
        return new ResponseEntity<>(assignmentDTOS, HttpStatus.OK);
    }

    @GetMapping("/assignments/class/{classSectionId}")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignmentsByClass(@PathVariable String classSectionId) {
        List<AssignmentDTO> assignmentDTOS = assignmentService.getAllAssignmentsByClass(classSectionId);
        return new ResponseEntity<>(assignmentDTOS, HttpStatus.OK);
    }
}
