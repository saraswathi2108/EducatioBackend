package com.project.student.education.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectWiseMarksEntryDTO {
    private Long recordId;
    private String studentId;
    private Double marksObtained;
    private String attendanceStatus;
    private String remarks;
}
