package com.project.student.education.DTO;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSubjectTimetableDTO {

    private String subjectId;
    private String subjectName;

    private String teacherId;
    private String teacherName;

    private List<String> weeklyDays = new ArrayList<>();
    private List<TimetableMiniDTO> periods = new ArrayList<>();
}
