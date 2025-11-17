package com.project.student.education.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClassSectionMiniDTO {
    private String classSectionId;
    private String className;         // e.g., "5"
    private String sectionName;       // e.g., "A"
}
