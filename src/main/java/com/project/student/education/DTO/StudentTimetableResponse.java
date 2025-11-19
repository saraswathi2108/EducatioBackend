package com.project.student.education.DTO;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentTimetableResponse {

    private String studentId;
    private String studentName;

    private ClassSectionMiniDTO classSection;   // className, section, academicYear

    private TeacherMiniDTO classTeacher;        // only id + name

    private List<StudentSubjectTimetableDTO> subjects; // each subject with teacher + periods

    private List<TimetableMiniDTO> todayTimetable;     // only today's entries

    private List<WeeklyTimetableDTO> fullWeekTimetable; // grouped by day
}
