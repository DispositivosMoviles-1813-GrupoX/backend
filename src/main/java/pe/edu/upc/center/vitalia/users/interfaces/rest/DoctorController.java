package pe.edu.upc.center.vitalia.users.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.vitalia.users.domain.services.DoctorCommandService;
import pe.edu.upc.center.vitalia.users.domain.services.DoctorQueryService;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.*;
import pe.edu.upc.center.vitalia.users.interfaces.rest.transform.DoctorResourceAssembler;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/doctors")
@Tag(name = "Doctors", description = "Operations related to Doctors")
public class DoctorController {

    private final DoctorCommandService doctorCommandService;
    private final DoctorQueryService doctorQueryService;

    public DoctorController(DoctorCommandService doctorCommandService, DoctorQueryService doctorQueryService) {
        this.doctorCommandService = doctorCommandService;
        this.doctorQueryService = doctorQueryService;
    }

    @Operation(
            summary = "Get all doctors",
            description = "Retrieve a list of all doctors",
            operationId = "getAllDoctors",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of doctors retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    )
            }
    )
    @GetMapping
    public List<DoctorResource> getAllDoctors() {
        return doctorQueryService.getAllDoctors().stream()
                .map(DoctorResourceAssembler::toResource)
                .toList();
    }

    @Operation(
            summary = "Get doctor by ID",
            description = "Retrieve a doctor's details by their ID",
            operationId = "getDoctorById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Doctor not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResource> getDoctorById(@PathVariable Long id) {
        return doctorQueryService.getDoctorById(id)
                .map(DoctorResourceAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new doctor",
            description = "Register a new doctor in the system",
            operationId = "createDoctor",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Doctor created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RuntimeException.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<DoctorResource> createDoctor(@RequestBody CreateDoctorResource resource) {
        Doctor doctor = DoctorResourceAssembler.toEntity(resource);
        Doctor saved = doctorCommandService.createDoctor(doctor);
        return ResponseEntity.status(201).body(DoctorResourceAssembler.toResource(saved));
    }

    @Operation(
            summary = "Update doctor",
            description = "Update the details of an existing doctor",
            operationId = "updateDoctor",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResource> updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorResource resource) {
        Doctor updatedDoctor = doctorCommandService.updateDoctor(id, resource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @Operation(
            summary = "Delete doctor",
            description = "Remove a doctor from the system",
            operationId = "deleteDoctor",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Doctor deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid ID supplied",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorCommandService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Assign schedule to doctor",
            description = "Assign a working schedule to a specific doctor",
            operationId = "assignSchedule",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor schedule assigned successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid schedule data",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{id}/schedule")
    public ResponseEntity<DoctorResource> assignSchedule(
            @PathVariable Long id,
            @RequestBody CreateScheduleResource scheduleResource) {
        Doctor updatedDoctor = doctorCommandService.assignShift(
                id,
                scheduleResource.day(),
                scheduleResource.startTime(),
                scheduleResource.endTime(),
                scheduleResource.appointmentId()
        );
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @Operation(
            summary = "Update doctor's schedule",
            description = "Update an existing schedule for a doctor",
            operationId = "updateSchedule",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Schedule updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid schedule data",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}/schedules/{scheduleId}")
    public ResponseEntity<DoctorResource> updateSchedule(
            @PathVariable Long id,
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleResource scheduleResource) {
        Doctor updatedDoctor = doctorCommandService.updateSchedule(id, scheduleId, scheduleResource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @Operation(
            summary = "Delete doctor's schedule",
            description = "Remove a schedule from a specific doctor",
            operationId = "deleteSchedule",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Schedule deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid schedule ID supplied",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}/schedules/{scheduleId}")
    public ResponseEntity<DoctorResource> deleteSchedule(
            @PathVariable Long id,
            @PathVariable Long scheduleId) {
        Doctor updatedDoctor = doctorCommandService.deleteSchedule(id, scheduleId);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @Operation(
            summary = "Add address to doctor",
            description = "Add an address to a specific doctor",
            operationId = "addAddress",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Address added successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid address data",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{id}/contactInfo/address")
    public ResponseEntity<DoctorResource> addAddress(
            @PathVariable Long id,
            @RequestBody AddressResource addressResource) {
        Doctor updatedDoctor = doctorCommandService.addAddress(id, addressResource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @Operation(
            summary = "Update address of doctor",
            description = "Update the address of a specific doctor",
            operationId = "updateAddress",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Address updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid address data",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}/contactInfo/address")
    public ResponseEntity<DoctorResource> updateAddress(
            @PathVariable Long id,
            @RequestBody AddressResource addressResource) {
        Doctor updatedDoctor = doctorCommandService.updateAddress(id, addressResource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<DoctorResource> getDoctorByUserId(@PathVariable Long userId) {
        // Usamos el servicio con el estilo directo que preferías
        var doctor = doctorQueryService.getDoctorByUserId(userId);

        if (doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(DoctorResourceAssembler.toResource(doctor.get()));
    }
}
