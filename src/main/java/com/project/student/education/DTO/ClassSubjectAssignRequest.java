package com.project.student.education.DTO;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class ClassSubjectAssignRequest {

    private String classSectionId;
    private List<String> subjectIds;
    private String teacherId;
}
