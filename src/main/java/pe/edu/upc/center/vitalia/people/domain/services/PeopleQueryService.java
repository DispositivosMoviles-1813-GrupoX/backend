package pe.edu.upc.center.vitalia.people.domain.services;

import pe.edu.upc.center.vitalia.people.domain.model.aggregates.People;

import java.util.List;
import java.util.Optional;

public interface PeopleQueryService {
    List<People> getAllPeople();
    Optional<People> getPeopleById(Long id);
}
