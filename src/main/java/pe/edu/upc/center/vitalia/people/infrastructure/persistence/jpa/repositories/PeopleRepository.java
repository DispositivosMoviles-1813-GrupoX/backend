package pe.edu.upc.center.vitalia.people.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.vitalia.people.domain.model.aggregates.People;

public interface PeopleRepository extends JpaRepository<People, Long> {
}
