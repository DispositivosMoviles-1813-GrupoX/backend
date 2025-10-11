package pe.edu.upc.center.vitalia.people.domain.services;

import pe.edu.upc.center.vitalia.people.domain.model.aggregates.People;

public interface PeopleCommandService {
    People createPeople(People people);
    People updatePeople(Long peopleId, People people);
    void deletePeople(Long peopleId);
}
