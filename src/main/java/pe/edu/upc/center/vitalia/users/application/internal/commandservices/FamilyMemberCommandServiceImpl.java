package pe.edu.upc.center.vitalia.users.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.vitalia.users.domain.services.FamilyMemberCommandService;
import pe.edu.upc.center.vitalia.users.infrastructure.persistence.jpa.repositories.FamilyMemberRepository;

@Service
@Transactional
public class FamilyMemberCommandServiceImpl implements FamilyMemberCommandService {

    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMemberCommandServiceImpl(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    @Override
    public FamilyMember createFamilyMember(FamilyMember familyMember) {
        return familyMemberRepository.save(familyMember);
    }

    @Override
    public FamilyMember updateFamilyMember(Long id, FamilyMember updatedMember) {
        return familyMemberRepository.findById(id).map(member -> {
            member.setRelationship(updatedMember.getRelationship());
            member.setLinkedResidentId(updatedMember.getLinkedResidentId());
            member.setFullName(updatedMember.getFullName());
            member.setContactInfo(updatedMember.getContactInfo());
            return familyMemberRepository.save(member);
        }).orElseThrow(() -> new RuntimeException("FamilyMember not found"));
    }

    @Override
    public void deleteFamilyMember(Long id) {
        familyMemberRepository.deleteById(id);
    }
}
