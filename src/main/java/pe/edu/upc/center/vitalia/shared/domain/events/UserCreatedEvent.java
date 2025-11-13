package pe.edu.upc.center.vitalia.shared.domain.events;

public record UserCreatedEvent(Long userId,
                               String username,
                               String emailAddress) {
}