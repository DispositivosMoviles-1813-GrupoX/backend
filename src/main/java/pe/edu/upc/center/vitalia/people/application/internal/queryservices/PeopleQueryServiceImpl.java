package pe.edu.upc.center.vitalia.people.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.people.domain.model.aggregates.People;
import pe.edu.upc.center.vitalia.people.domain.services.PeopleQueryService;
import pe.edu.upc.center.vitalia.people.infrastructure.persistence.jpa.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleQueryServiceImpl implements PeopleQueryService {

    private final PeopleRepository peopleRepository;

    public PeopleQueryServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public List<People> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Override
    public Optional<People> getPeopleById(Long id) {
        return peopleRepository.findById(id);
    }
}
