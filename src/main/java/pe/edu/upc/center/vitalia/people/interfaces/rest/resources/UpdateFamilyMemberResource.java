package pe.edu.upc.center.vitalia.people.interfaces.rest.resources;

import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.FullName;

public record UpdateFamilyMemberResource(
        String relationship,
        Long linkedResidentId,
        FullName fullName
        //ContactInfo contactInfo
) {}
