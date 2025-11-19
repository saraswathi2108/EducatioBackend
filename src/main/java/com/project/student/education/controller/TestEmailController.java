//package com.project.student.education.controller;
//
//import com.project.student.education.service.EmailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/test-email")
//@RequiredArgsConstructor
//public class TestEmailController {
//
//    private final EmailService emailService;
//
//    @PostMapping
//    public ResponseEntity<?> sendTestEmail(@RequestBody Map<String, String> body) {
//        String to = body.get("to");
//
//        if (to == null || to.isBlank()) {
//            return ResponseEntity.badRequest()
//                    .body(Map.of("error", "Please provide 'to' email address"));
//        }
//
//        emailService.sendSimpleEmail(
//                to,
//                "Test Email from Spring Boot",
//                "Congratulations! Your email setup is working correctly 🎉"
//        );
//
//        return ResponseEntity.ok(Map.of("message", "Email sent! Check your inbox."));
//    }
//}
