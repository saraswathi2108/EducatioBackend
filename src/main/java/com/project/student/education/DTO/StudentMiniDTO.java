package com.project.student.education.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentMiniDTO {
    private String studentId;
    private String fullName;
    private String rollNumber;
}
