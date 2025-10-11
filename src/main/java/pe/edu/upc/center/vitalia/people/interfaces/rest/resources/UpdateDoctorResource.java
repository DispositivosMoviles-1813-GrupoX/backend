package pe.edu.upc.center.vitalia.people.interfaces.rest.resources;

import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.FullName;

public record UpdateDoctorResource(
        String licenseNumber,
        String specialty,
        //UpdateScheduleResource schedule,
        FullName fullName,
        ContactInfo contactInfo
) {}

