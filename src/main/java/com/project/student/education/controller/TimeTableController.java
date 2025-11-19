package com.project.student.education.controller;


import com.project.student.education.DTO.StudentTimetableResponse;
import com.project.student.education.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class TimeTableController {


    private final StudentService studentService;

    public TimeTableController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}/timetable")
    public ResponseEntity<StudentTimetableResponse> getStudentTimetable(
            @PathVariable String studentId
    ) {
        StudentTimetableResponse response =
                studentService.getStudentTimetable(studentId);

        return ResponseEntity.ok(response);
    }

}


