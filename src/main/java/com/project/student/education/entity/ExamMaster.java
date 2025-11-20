package com.project.student.education.entity;

import com.project.student.education.enums.ExamStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_master")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ExamMaster {

    @Id
    @Column(name = "exam_id", length = 50)
    private String examId;

    @Column(nullable = false)
    private String examName;



    private String academicYear;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ExamStatus status;


    private String createdBy;
    private LocalDateTime createdAt;

    private String updatedBy;
    private LocalDateTime updatedAt;
}
