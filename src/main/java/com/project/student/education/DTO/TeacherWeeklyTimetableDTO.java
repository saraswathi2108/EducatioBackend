package com.project.student.education.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherWeeklyTimetableDTO {

    private String teacherId;
    private String teacherName;

    private List<DayEntry> weeklyTimetable;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DayEntry {
        private String day;       // MONDAY
        private String date;      // 2025-11-20
        private List<Period> periods;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Period {
        private String date;              // same day's full date
        private String classSectionId;
        private String className;
        private String section;

        private String subjectId;
        private String subjectName;

        private String startTime;  // 10:00 AM
        private String endTime;    // 11:00 AM
    }
}
