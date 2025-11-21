package com.project.student.education.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentTransportDTO {

    private String studentId;

    private String routeName;

    private String pickupStop;
    private String dropStop;

    private String pickupTime;
    private String dropTime;

    private String vehicleName;
    private String vehicleNumber;

    private String driverName;
    private String driverPhone;

    private String feeStatus;
}
