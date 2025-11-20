package com.project.student.education.DTO;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class BulkScheduleRequest {

    private String examId;
    private List<String> classSectionId;
    private String subjectId;

    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;


}
