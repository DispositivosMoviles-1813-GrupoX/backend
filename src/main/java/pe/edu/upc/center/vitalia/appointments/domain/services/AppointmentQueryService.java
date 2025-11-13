package pe.edu.upc.center.vitalia.appointments.domain.services;

import pe.edu.upc.center.vitalia.appointments.domain.model.aggregates.Appointment;
import pe.edu.upc.center.vitalia.appointments.domain.model.queries.GetAllAppointmentsQuery;
import pe.edu.upc.center.vitalia.appointments.domain.model.queries.GetAppointmentByDoctorIdQuery;
import pe.edu.upc.center.vitalia.appointments.domain.model.queries.GetAppointmentByIdQuery;
import pe.edu.upc.center.vitalia.appointments.domain.model.queries.GetAppointmentByResidentIdQuery;

import java.util.List;
import java.util.Optional;

public interface AppointmentQueryService {
    List<Appointment> handle(GetAllAppointmentsQuery query);
    Optional<Appointment> handle(GetAppointmentByIdQuery query);
    List<Appointment> handle(GetAppointmentByResidentIdQuery query);
    List<Appointment> handle(GetAppointmentByDoctorIdQuery query);
}
