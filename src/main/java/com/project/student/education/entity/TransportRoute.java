package com.project.student.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TransportRoute {

    @Id
    private String routeId;

    private String routeName;

    private String pickupStartTime;
    private String dropStartTime;

    private String vehicleName;
    private String vehicleNumber;

    private String driverName;
    private String driverPhone;
}
