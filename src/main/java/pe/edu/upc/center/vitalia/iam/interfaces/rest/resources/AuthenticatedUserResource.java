package pe.edu.upc.center.vitalia.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, String token) {
}
