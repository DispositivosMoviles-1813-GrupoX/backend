package pe.edu.upc.center.vitalia.people.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.vitalia.people.domain.model.entities.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
}
