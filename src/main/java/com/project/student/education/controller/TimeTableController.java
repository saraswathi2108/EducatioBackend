package com.project.student.education.controller;


import com.project.student.education.DTO.CreateTimetableRequest;
import com.project.student.education.DTO.StudentWeeklyTimetableDTO;
import com.project.student.education.DTO.TeacherWeeklyTimetableDTO;
import com.project.student.education.service.StudentService;
import com.project.student.education.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class TimeTableController {


    private final StudentService studentService;
    private final TeacherService teacherService;

    public TimeTableController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }


    @PostMapping("/create/timetable")
    public ResponseEntity<String> createTimetable(
            @RequestBody CreateTimetableRequest request
    ) {
        studentService.createWeeklyTimetable(request);
        return ResponseEntity.ok("Timetable created successfully!");
    }

    @PutMapping("/update/timetable")
    public ResponseEntity<String> updateTimetable(@RequestBody CreateTimetableRequest request)
    {
        studentService.updateWeeklyTimeTable(request);
        return ResponseEntity.ok("Timetable updated successfully!");
    }


    @GetMapping("/{studentId}/weekly-timetable")
    public ResponseEntity<StudentWeeklyTimetableDTO> getStudentWeeklyTimetable(
            @PathVariable String studentId,
            @RequestParam(required = false) String weekStart
    ) {
        LocalDate ref;
        if (weekStart == null || weekStart.isEmpty()) {
            ref = LocalDate.now();
        } else {
            ref = LocalDate.parse(weekStart);
        }

        StudentWeeklyTimetableDTO dto = studentService.getStudentWeeklyTimetableWithDates(studentId, ref);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/teacher/{teacherId}/weekly-timetable")
    public ResponseEntity<TeacherWeeklyTimetableDTO> getTeacherTimetable(
            @PathVariable String teacherId,
            @RequestParam(required = false) String weekStart
    ) {
        LocalDate ref = (weekStart == null || weekStart.isEmpty())
                ? LocalDate.now()
                : LocalDate.parse(weekStart);

        return ResponseEntity.ok(
                teacherService.getTeacherWeeklyTimetable(teacherId, ref)
        );
    }


    @GetMapping("/teacher/{teacherId}/class-timetable")
    public ResponseEntity<TeacherWeeklyTimetableDTO> getClassTeacherTimetable(
            @PathVariable String teacherId,
            @RequestParam(required = false) String weekStart
    ) {
        LocalDate ref = (weekStart == null || weekStart.isEmpty())
                ? LocalDate.now()
                : LocalDate.parse(weekStart);

        return ResponseEntity.ok(
                teacherService.getClassTeacherTimetable(teacherId, ref)
        );
    }




}


