package com.project.student.education.service;


import com.project.student.education.DTO.StudentTransportDTO;
import com.project.student.education.DTO.TransportAssignRequest;
import com.project.student.education.entity.IdGenerator;
import com.project.student.education.entity.TransportRoute;
import com.project.student.education.repository.TransportRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor



public class TransportService {

    private final IdGenerator idGenerator;
    private final TransportRouteRepository transportRouteRepository;

    public TransportRoute createRoute(TransportRoute route) {
        route.setRouteId(idGenerator.generateId("TRT"));
        return transportRouteRepository.save(route);
    }

    public TransportRoute updateRoute(String routeId, TransportRoute updated) {
        TransportRoute existing = getRoute(routeId);

        existing.setRouteName(updated.getRouteName());
        existing.setPickupStartTime(updated.getPickupStartTime());
        existing.setDropStartTime(updated.getDropStartTime());
        existing.setVehicleName(updated.getVehicleName());
        existing.setVehicleNumber(updated.getVehicleNumber());
        existing.setDriverName(updated.getDriverName());
        existing.setDriverPhone(updated.getDriverPhone());

        return transportRouteRepository.save(existing);
    }

    public TransportRoute getRoute(String routeId) {
        return transportRouteRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }


//    public StudentTransportDTO assignTransport(String studentId, TransportAssignRequest assignRequest) {
//
//    }
}
