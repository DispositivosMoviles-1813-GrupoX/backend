package pe.edu.upc.center.vitalia.people.interfaces.rest.resources;

import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.FullName;

public record FamilyMemberResource(
        Long id,
        String relationship,
        Long linkedResidentId,
        FullName fullName,
        ContactInfo contactEmail
) {}
