package pe.edu.upc.center.vitalia.users.domain.services;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.FamilyMember;

public interface FamilyMemberCommandService {
    FamilyMember createFamilyMember(FamilyMember familyMember);

    FamilyMember updateFamilyMember(Long id, FamilyMember updatedMember);

    void deleteFamilyMember(Long id);
}
