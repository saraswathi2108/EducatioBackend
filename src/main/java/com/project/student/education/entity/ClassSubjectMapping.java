package com.project.student.education.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class_subject_mapping",
        uniqueConstraints = @UniqueConstraint(columnNames = {"class_section_id", "subject_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSubjectMapping {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "class_section_id")
    private ClassSection classSection;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
