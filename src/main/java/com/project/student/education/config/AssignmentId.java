package com.project.student.education.config;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentId implements Serializable {

    @Column(name = "assignment_id")
    private String assignmentId;

    @Column(name = "subject_id")
    private String subjectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssignmentId that)) return false;
        return Objects.equals(assignmentId, that.assignmentId)
                && Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId, subjectId);
    }
}
