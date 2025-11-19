//package com.project.student.education.repository;
//
//import com.project.student.education.DTO.PasswordResetToken;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
//    Optional<PasswordResetToken> findByEmailAndOtp(String email, String otp);
//}
