package pe.edu.upc.center.vitalia.people.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.people.domain.model.aggregates.People;
import pe.edu.upc.center.vitalia.people.domain.services.PeopleCommandService;
import pe.edu.upc.center.vitalia.people.infrastructure.persistence.jpa.repositories.PeopleRepository;

import java.util.Optional;

@Service
public class PeopleCommandServiceImpl implements PeopleCommandService {

    private final PeopleRepository peopleRepository;

    public PeopleCommandServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public People createPeople(People people) {
        return peopleRepository.save(people);
    }

    @Override
    public People updatePeople(Long peopleId, People people) {
        Optional<People> existingUser = peopleRepository.findById(peopleId);
        if (existingUser.isPresent()) {
            People updatedUser = existingUser.get();
            updatedUser.setName(people.getName());
            updatedUser.setEmail(people.getEmail());
            updatedUser.setRole(people.getEmail());
            // Agrega aquí otros campos a actualizar
            return peopleRepository.save(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + peopleId);
        }
    }

    @Override
    public void deletePeople(Long peopleId) {
        peopleRepository.deleteById(peopleId);
    }
}
