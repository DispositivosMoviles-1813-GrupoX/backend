package pe.edu.upc.center.vitalia.shared.domain.events;

public record AddedScheduled(Long doctorId,
                             String day,
                             String startTime,
                             String endTime,
                             Long appointmentId,
                             String emailAddress,
                             Long userId) {
}
