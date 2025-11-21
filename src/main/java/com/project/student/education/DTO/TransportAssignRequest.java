package com.project.student.education.DTO;

import lombok.Data;

@Data
public class TransportAssignRequest {

    private String routeId;

    private String pickupStop;
    private String dropStop;

    private String pickupTime;
    private String dropTime;

    private String feeStatus;
}
