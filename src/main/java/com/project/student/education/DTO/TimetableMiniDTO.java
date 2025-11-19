package com.project.student.education.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimetableMiniDTO {

    private String day;
    private String startTime;
    private String endTime;
    private String subjectName;
    private String teacherName;
}
