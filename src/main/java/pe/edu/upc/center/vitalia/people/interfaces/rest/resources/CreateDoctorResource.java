package pe.edu.upc.center.vitalia.people.interfaces.rest.resources;

import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.FullName;

public record CreateDoctorResource(
        String licenseNumber,
        String specialty,
        //Schedule schedule,
        FullName fullName,
        ContactInfo contactInfo
) {}
