package com.project.student.education.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTimetableRequest {

    private String classSectionId;

    private List<PeriodRequest> periods;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PeriodRequest {

        private String day;
        private String subjectId;
        private String teacherId;
        private String startTime;
        private String endTime;
    }
}
