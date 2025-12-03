package pe.edu.upc.center.vitalia.users.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.vitalia.users.domain.model.queries.GetFamilyMemberByResidentIdQuery;
import pe.edu.upc.center.vitalia.users.domain.services.FamilyMemberQueryService;
import pe.edu.upc.center.vitalia.users.infrastructure.persistence.jpa.repositories.FamilyMemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FamilyMemberQueryServiceImpl implements FamilyMemberQueryService {

    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMemberQueryServiceImpl(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    @Override
    public List<FamilyMember> getAllFamilyMembers() {
        return familyMemberRepository.findAll();
    }

    @Override
    public Optional<FamilyMember> getFamilyMemberById(Long id) {
        return familyMemberRepository.findById(id);
    }

  @Override
  public Optional<FamilyMember> handle(GetFamilyMemberByResidentIdQuery query) {
    return this.familyMemberRepository.findByLinkedResidentId(query.residentId());
  }
}
