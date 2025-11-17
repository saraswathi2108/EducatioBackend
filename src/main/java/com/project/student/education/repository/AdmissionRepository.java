package com.project.student.education.repository;

import com.project.student.education.entity.Admission;
import com.project.student.education.enums.AdmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AdmissionRepository extends JpaRepository<Admission,String> {
    Optional<Admission> findByAdmissionNumber(String admissionNumber);

    List<Admission> findByStatus(AdmissionStatus status);

    boolean existsByAadhaarCardNumber(String aadhaarCardNumber);
}
