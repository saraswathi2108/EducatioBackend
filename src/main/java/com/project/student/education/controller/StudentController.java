package com.project.student.education.controller;

import com.project.student.education.DTO.StudentDTO;
import com.project.student.education.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/allStudents")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO>studentDTOS=studentService.getAllStudents();
        return ResponseEntity.ok(studentDTOS);

    }
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable String studentId, @RequestBody StudentDTO studentDTO) {
        StudentDTO studentDTO1=studentService.updateStudent(studentId,studentDTO);
        return ResponseEntity.ok(studentDTO1);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<StudentDTO>deleteStudent(@PathVariable String studentId) {
        StudentDTO studentDTO1=studentService.deleteStudent(studentId);
        return ResponseEntity.ok(studentDTO1);
    }

    @PutMapping("/{studentId}/transfer")
    public ResponseEntity<StudentDTO> transferStudent(
            @PathVariable String studentId,
            @RequestParam String targetClassSectionId) {

        StudentDTO dto = studentService.transferStudent(studentId, targetClassSectionId);
        return ResponseEntity.ok(dto);
    }


}
