package com.project.student.education.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyTimetableDTO {

    private String day;
    private List<TimetableMiniDTO> list = new ArrayList<>();
}
