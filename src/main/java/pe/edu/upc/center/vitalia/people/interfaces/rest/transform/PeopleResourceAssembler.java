package pe.edu.upc.center.vitalia.people.interfaces.rest.transform;

import pe.edu.upc.center.vitalia.people.domain.model.aggregates.People;
import pe.edu.upc.center.vitalia.people.interfaces.rest.resources.CreatePeopleResource;
import pe.edu.upc.center.vitalia.people.interfaces.rest.resources.PeopleResource;

public class PeopleResourceAssembler {

    public static PeopleResource toResource(People people) {
        return new PeopleResource(
                people.getId(),
                people.getName(),
                people.getEmail(),
                people.getRole()
                // agrega otros campos si tienes más
        );
    }

    public static People toEntity(CreatePeopleResource resource) {
        People people = new People();
        people.setName(resource.name());
        people.setEmail(resource.email());
        people.setRole(resource.role());
        // agrega otros setters si tienes más
        return people;
    }
}
