package com.project.student.education.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectStudentMarksDTO {

    private Long recordId;
    private String studentId;
    private String studentName;

    private Double maxMarks;
    private Double passMarks;

    private Double marksObtained;
    private String attendanceStatus;
}
