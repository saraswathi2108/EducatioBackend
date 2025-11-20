package com.project.student.education.DTO;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComprehensiveScheduleRequest {

    private String examId;
    private List<String> classSectionIds;

    private List<ScheduleEntry> schedules;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScheduleEntry {
        private String subjectId;
        private LocalDate examDate;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
