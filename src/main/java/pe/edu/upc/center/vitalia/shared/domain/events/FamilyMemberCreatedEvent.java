package pe.edu.upc.center.vitalia.shared.domain.events;

public record FamilyMemberCreatedEvent(String relationship,
                                       Long linkedResidentId,
                                       String firstName,
                                       String lastName,
                                       String emailAddress,
                                       Long userId) {
}
