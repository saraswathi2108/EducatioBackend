package com.project.student.education.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceRequest {

    private String classSectionId;
    private LocalDate date;

    private List<AttendanceEntryDTO> entries;
}
