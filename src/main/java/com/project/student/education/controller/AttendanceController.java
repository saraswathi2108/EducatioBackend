//package com.project.student.education.controller;
//
//import com.project.student.education.DTO.AttendanceRequest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/student/attendance")
//public class AttendanceController {
//
//
//    @PostMapping("/mark")

//    public ResponseEntity<AttendanceRequest> markAttendance(
//            @RequestBody AttendanceRequest request,
//            @RequestHeader("markedBy") String markedBy
//    ) {
//        return ResponseEntity.ok(attendanceService.markAttendance(request, markedBy));
//    }
//}
