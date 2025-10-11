package pe.edu.upc.center.vitalia.people.domain.services;

import pe.edu.upc.center.vitalia.people.domain.model.aggregates.FamilyMember;

public interface FamilyMemberCommandService {
    FamilyMember createFamilyMember(FamilyMember familyMember);

    FamilyMember updateFamilyMember(Long id, FamilyMember updatedMember);

    void deleteFamilyMember(Long id);
}
