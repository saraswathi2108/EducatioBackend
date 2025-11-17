package com.project.student.education.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "id_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdSequence {

    @Id
    private String prefix;

    private long lastNumber;
}
