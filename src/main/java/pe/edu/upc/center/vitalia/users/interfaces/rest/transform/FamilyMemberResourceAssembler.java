package pe.edu.upc.center.vitalia.users.interfaces.rest.transform;

import pe.edu.upc.center.vitalia.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.CreateFamilyMemberResource;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.FamilyMemberResource;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.UpdateFamilyMemberResource;

public class FamilyMemberResourceAssembler {

    public static FamilyMemberResource toResource(FamilyMember familyMember) {
        return new FamilyMemberResource(
                familyMember.getId(),
                familyMember.getRelationship(),
                familyMember.getLinkedResidentId(),
                familyMember.getFullName(),
                familyMember.getContactInfo()
        );
    }

    public static FamilyMember toEntity(CreateFamilyMemberResource resource) {
        FamilyMember familyMember = new FamilyMember();
        familyMember.setRelationship(resource.relationship());
        familyMember.setLinkedResidentId(resource.linkedResidentId());
        familyMember.setFullName(resource.fullName());
        //familyMember.setContactInfo(resource.contactInfo());
        return familyMember;
    }

    public static FamilyMember toEntity(UpdateFamilyMemberResource resource) {
        FamilyMember familyMember = new FamilyMember();
        familyMember.setRelationship(resource.relationship());
        familyMember.setLinkedResidentId(resource.linkedResidentId());
        familyMember.setFullName(resource.fullName());
        //familyMember.setContactInfo(resource.contactInfo());
        return familyMember;
    }
}
