package pe.edu.upc.center.vitalia.users.interfaces.rest.resources;

public record CreateUserResource(
        String name,
        String email,
        String role
) {
}
