package pe.edu.upc.center.vitalia.shared.domain.events;

public record DoctorCreatedEvent(String licenseNumber,
                                 String specialty,
                                 String firstName,
                                 String lastName,
                                 Long doctorId) {
}
