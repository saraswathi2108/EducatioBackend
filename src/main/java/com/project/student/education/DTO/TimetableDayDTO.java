package com.project.student.education.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDayDTO {
    private LocalDate examDate;
    private String dayName;
    private List<TimetableSubjectDTO> subjects;
}
