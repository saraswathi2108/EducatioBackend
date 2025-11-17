package com.project.student.education.config;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentSubmissionId implements Serializable {

    private AssignmentId assignmentId;
    private Long submissionNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssignmentSubmissionId that)) return false;
        return Objects.equals(assignmentId, that.assignmentId)
                && Objects.equals(submissionNumber, that.submissionNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId, submissionNumber);
    }
}
