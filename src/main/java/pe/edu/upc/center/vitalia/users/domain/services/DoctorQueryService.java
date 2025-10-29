package pe.edu.upc.center.vitalia.users.domain.services;

import pe.edu.upc.center.vitalia.users.domain.model.aggregates.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorQueryService {
    List<pe.edu.upc.center.vitalia.users.domain.model.aggregates.Doctor> getAllDoctors();
    Optional<pe.edu.upc.center.vitalia.users.domain.model.aggregates.Doctor> getDoctorById(Long id);
}
