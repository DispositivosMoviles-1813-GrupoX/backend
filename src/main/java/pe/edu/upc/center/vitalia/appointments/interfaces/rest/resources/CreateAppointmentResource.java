package pe.edu.upc.center.vitalia.appointments.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateAppointmentResource(
    @Schema(description = "The unique identifier of the appointment", example = "1")
    Long residentId,

    @Schema(description = "The unique identifier of the doctor", example = "1")
    Long doctorId,

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The date of the appointment", example = "2025-01-01")
    LocalDate date,

    @JsonFormat(pattern = "HH:mm")
    @Schema(description = "The time of the appointment", type = "string", example = "12:00")
    LocalTime time,

    @Schema(description = "The status of the appointment", example = "PENDING")
    String status
){}