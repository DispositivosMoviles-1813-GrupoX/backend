package pe.edu.upc.center.vitalia.users.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
