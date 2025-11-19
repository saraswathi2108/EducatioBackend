package com.project.student.education.controller;

import com.project.student.education.DTO.AdmissionDTO;
import com.project.student.education.DTO.ApproveAdmissionRequest;
import com.project.student.education.DTO.StudentDTO;
import com.project.student.education.entity.Admission;
import com.project.student.education.service.AdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class AdmissionController {

    @Autowired
    private  AdmissionService admissionService;



    @PostMapping(value = "/admission", consumes = "multipart/form-data")
    public ResponseEntity<AdmissionDTO> submitAdmission(
            @RequestPart("data") Admission admission,
            @RequestPart(value = "photo", required = false) MultipartFile photoUrl
    ) {
        AdmissionDTO admissionDTO = admissionService.submitAdmission(admission, photoUrl);
        return new ResponseEntity<>(admissionDTO, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/approve/{admissionNumber}")
    public ResponseEntity<StudentDTO> approveAdmission(
            @PathVariable String admissionNumber,
            @RequestBody ApproveAdmissionRequest request
    ) {
        StudentDTO studentDTO = admissionService.approveAdmission(
                admissionNumber,
                request.getApprovedBy(),
                request.getAcademicYear()
        );
        return ResponseEntity.ok(studentDTO);
    }

   // @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/admissions")
    public ResponseEntity<List<AdmissionDTO>> getAllAdmissions() {
        return ResponseEntity.ok(admissionService.getAllAdmissions());
    }
   // @PreAuthorize("hasRole('ROLE_ADMIN')")

    @PostMapping("/reject/{admissionNumber}")
    public ResponseEntity<AdmissionDTO> rejectAdmission(
            @PathVariable String admissionNumber,
            @RequestParam(required = false) String reason
    ) {
        AdmissionDTO admissionDTO = admissionService.rejectAdmission(admissionNumber, reason);
        return ResponseEntity.ok(admissionDTO);
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admission/{admissionNumber}")
    public ResponseEntity<AdmissionDTO> deleteAdmission(@PathVariable String admissionNumber) {
        AdmissionDTO admissionDTO=admissionService.deleteAdmission(admissionNumber);
        return ResponseEntity.ok(admissionDTO);
    }
    //PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admissions/pending")
    public ResponseEntity<List<AdmissionDTO>> getPendingAdmissions() {
        return ResponseEntity.ok(admissionService.getAdmissionsByStatus("PENDING"));
    }





}
