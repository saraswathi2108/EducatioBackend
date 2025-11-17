package com.project.student.education.DTO;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ExamMasterDTO {

    private String examId;
    private String examName;
    private String examType;
    private String academicYear;

    private LocalDate startDate;
    private LocalDate endDate;

    private String status;

    private String weightageJson;

    private String createdBy;
    private LocalDateTime createdAt;

    private String updatedBy;
    private LocalDateTime updatedAt;
}
