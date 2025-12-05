package pe.edu.upc.center.vitalia.users.domain.services;

import pe.edu.upc.center.vitalia.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.vitalia.users.domain.model.queries.GetFamilyMemberByResidentIdQuery;

import java.util.List;
import java.util.Optional;

public interface FamilyMemberQueryService {
    List<FamilyMember> getAllFamilyMembers();

    Optional<FamilyMember> getFamilyMemberById(Long id);

    Optional<FamilyMember> handle(GetFamilyMemberByResidentIdQuery query);
}
