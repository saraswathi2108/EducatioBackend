package com.project.student.education.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "subjects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Subject {

    @Id
    private String subjectId;

    @Column(nullable = false)
    private String subjectName;

    @Column(nullable = false, unique = true)
    private String subjectCode;

    private Boolean active = true;
}
