package pe.edu.upc.center.vitalia.users.interfaces.rest.resources;

import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.FullName;

public record FamilyMemberResource(
        Long id,
        String relationship,
        Long linkedResidentId,
        FullName fullName,
        ContactInfo contactEmail
) {}
