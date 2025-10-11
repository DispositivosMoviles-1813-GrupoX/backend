package pe.edu.upc.center.vitalia.people.interfaces.rest.resources;

public record PeopleResource(
        Long id,
        String name,
        String email,
        String role
) {
}
