package pe.edu.upc.center.vitalia.shared.domain.events;

import java.util.List;

public record UserCreatedEvent(Long userId,
                               String username,
                               String emailAddress,
                               List<String> roles) {
}