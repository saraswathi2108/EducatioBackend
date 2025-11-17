package com.project.student.education.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSubjectMappingDTO {
    private String id;
    private String classSectionId;
    private String subjectId;
    private String subjectName;
    private String teacherId;
    private String teacherName;
}
