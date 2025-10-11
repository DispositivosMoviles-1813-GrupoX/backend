package pe.edu.upc.center.vitalia.users.interfaces.rest.resources;

import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.FullName;

public record UpdateDoctorResource(
        String licenseNumber,
        String specialty,
        //UpdateScheduleResource schedule,
        FullName fullName,
        ContactInfo contactInfo
) {}

