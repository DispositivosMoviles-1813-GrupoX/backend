package pe.edu.upc.center.vitalia.people.domain.services;

import pe.edu.upc.center.vitalia.people.domain.model.aggregates.FamilyMember;

import java.util.List;
import java.util.Optional;

public interface FamilyMemberQueryService {
    List<FamilyMember> getAllFamilyMembers();

    Optional<FamilyMember> getFamilyMemberById(Long id);
}
