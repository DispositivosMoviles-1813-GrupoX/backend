package pe.edu.upc.center.vitalia.users.interfaces.rest.resources;

public record ScheduleResource(
        Long id,          // <--- Lo que falta en tu constructor
        String day,
        String startTime,
        String endTime,
        Long appointmentId
) {}
