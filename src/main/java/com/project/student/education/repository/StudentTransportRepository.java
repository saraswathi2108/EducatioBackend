package com.project.student.education.repository;

import com.project.student.education.entity.StudentTransport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentTransportRepository extends JpaRepository<StudentTransport, String> {
    Optional<StudentTransport> findByStudentId(String studentId);
}
