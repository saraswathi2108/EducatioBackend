package com.project.student.education.DTO;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MarksEntryRequest {

    private Long recordId;
    private String examId;
    private String studentId;
    private String subjectId;

    private Double marksObtained;
    private String attendanceStatus;
    private String remarks;
}
