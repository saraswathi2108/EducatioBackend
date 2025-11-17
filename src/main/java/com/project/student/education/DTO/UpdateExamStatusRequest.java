package com.project.student.education.DTO;

import com.project.student.education.enums.ExamStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateExamStatusRequest {
    private ExamStatus status;       // e.g. PUBLISHED, SCHEDULED
    private String updatedBy;
}
