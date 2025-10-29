package pe.edu.upc.center.vitalia.users.interfaces.rest.transform;

import pe.edu.upc.center.vitalia.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.Schedule;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.CreateDoctorResource;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.DoctorResource;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.ScheduleResource;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.UpdateDoctorResource;

import java.util.ArrayList;

public class DoctorResourceAssembler {

    public static DoctorResource toResource(Doctor doctor) {
        return new DoctorResource(
                doctor.getId(),
                doctor.getLicenseNumber(),
                doctor.getSpecialty(),
                doctor.getSchedules(),
                doctor.getFullName(),
                doctor.getContactInfo()
        );
    }

    public static Doctor toEntity(CreateDoctorResource resource) {
        Doctor doctor = new Doctor();
        doctor.setLicenseNumber(resource.licenseNumber());
        doctor.setSpecialty(resource.specialty());
        doctor.setFullName(resource.fullName());
        doctor.setSchedules(new ArrayList<>());  // Inicializa vacío
        doctor.setContactInfo(resource.contactInfo());
        return doctor;
    }

    public static Doctor toEntity(UpdateDoctorResource resource, Doctor existing) {
        existing.setLicenseNumber(resource.licenseNumber());
        existing.setSpecialty(resource.specialty());
        existing.setFullName(resource.fullName());
        existing.setContactInfo(resource.contactInfo());
        return existing;
    }
    public static ScheduleResource toScheduleResource(Schedule schedule) {
        return new ScheduleResource(
                schedule.getId(),
                schedule.getDay(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getAppointmentId()
        );
    }
    public static Schedule toEntity(ScheduleResource resource) {
        Schedule schedule = new Schedule();
        schedule.setId(resource.id());
        schedule.setDay(resource.day());
        schedule.setStartTime(resource.startTime());
        schedule.setEndTime(resource.endTime());
        schedule.setAppointmentId(resource.appointmentId());
        return schedule;
    }


}
