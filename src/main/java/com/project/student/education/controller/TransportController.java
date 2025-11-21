package com.project.student.education.controller;


import com.project.student.education.DTO.ComprehensiveScheduleRequest;
import com.project.student.education.DTO.StudentTransportDTO;
import com.project.student.education.DTO.TransportAssignRequest;
import com.project.student.education.entity.TransportRoute;
import com.project.student.education.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/transport")
@RequiredArgsConstructor
public class TransportController {

    private final TransportService transportService;




//    @Autowired
//    private TransportService transportService;
//    @PostMapping("/assign/{studentId}")
//    public ResponseEntity<StudentTransportDTO> assign(@PathVariable String studentId, @RequestBody TransportAssignRequest assignRequest) {
//        return ResponseEntity.ok(transportService.assignTransport(studentId,assignRequest));
//
//    }

    @PostMapping("/route")
    public ResponseEntity<TransportRoute> create(@RequestBody TransportRoute route) {
        return ResponseEntity.ok(transportService.createRoute(route));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportRoute> update(
            @PathVariable String id,
            @RequestBody TransportRoute route) {
        return ResponseEntity.ok(transportService.updateRoute(id, route));
    }




}
