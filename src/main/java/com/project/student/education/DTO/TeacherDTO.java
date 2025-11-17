package com.project.student.education.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {

    private String teacherId;
    private String teacherName;
    private String email;
    private String phone;
    private String qualification;
    private String gender;
    private int experience;
    private String address;
}
