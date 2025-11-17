package com.project.student.education.DTO;

import com.project.student.education.enums.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private Role role; // Optional, default handled in service
}
