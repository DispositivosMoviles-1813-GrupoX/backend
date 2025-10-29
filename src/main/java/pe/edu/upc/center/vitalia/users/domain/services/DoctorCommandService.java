package pe.edu.upc.center.vitalia.users.domain.services;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.AddressResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.ScheduleResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateDoctorResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateScheduleResource;

public interface DoctorCommandService {
    Doctor createDoctor(Doctor doctor);


    Doctor updateDoctor(Long id, UpdateDoctorResource resource);

    void deleteDoctor(Long id);
    Doctor addSchedule(Long doctorId, ScheduleResource resource);
    Doctor updateSchedule(Long doctorId, Long scheduleId, UpdateScheduleResource resource);
    Doctor deleteSchedule(Long doctorId, Long scheduleId);
    Doctor addAddress(Long doctorId, AddressResource addressResource);
    Doctor updateAddress(Long doctorId, AddressResource addressResource);

    Doctor assignShift(Long doctorId, String day, String startTime, String endTime, Long appointmentId);
}
