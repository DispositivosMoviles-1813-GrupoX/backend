package pe.edu.upc.center.vitalia.people.interfaces.rest.resources;

public record CreatePeopleResource(
        String name,
        String email,
        String role
) {
}
