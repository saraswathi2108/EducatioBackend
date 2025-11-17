package com.project.student.education.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    private String teacherId;

    @Column(nullable = false)
    private String teacherName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
    private String qualification;
    private String gender;
    private int experience;

    private String address;



//    @OneToMany(mappedBy = "teacher")
//    private List<Subject> subjects;


    @OneToOne(mappedBy = "classTeacher")
    private ClassSection assignedSection;
}
