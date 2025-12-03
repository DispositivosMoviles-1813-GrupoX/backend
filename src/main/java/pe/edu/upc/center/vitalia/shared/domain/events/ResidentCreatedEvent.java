package pe.edu.upc.center.vitalia.shared.domain.events;

public record ResidentCreatedEvent(Long residentId,
                                   String dni,
                                   String firstName,
                                   String lastName,
                                   String gender,
                                   String city,
                                   String country,
                                   Long familyMemberId,
                                   String familyMemberName,
                                   Long familyMemberUserId,
                                   String familyMemberEmail) {
}
