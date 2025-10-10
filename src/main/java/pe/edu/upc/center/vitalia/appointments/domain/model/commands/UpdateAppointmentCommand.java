package pe.edu.upc.center.vitalia.appointments.domain.model.commands;

import pe.edu.upc.center.vitalia.appointments.domain.model.valueobjects.DateTime;
import pe.edu.upc.center.vitalia.appointments.domain.model.valueobjects.DoctorId;
import pe.edu.upc.center.vitalia.appointments.domain.model.valueobjects.ResidentId;
import pe.edu.upc.center.vitalia.appointments.domain.model.valueobjects.Status;

public record UpdateAppointmentCommand(
    Long appointmentId,
    ResidentId residentId,
    DoctorId doctorId,
    DateTime dateTime,
    Status status
) {}
