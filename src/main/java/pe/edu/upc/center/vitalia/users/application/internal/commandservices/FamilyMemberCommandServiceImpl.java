package pe.edu.upc.center.vitalia.users.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.iam.interfaces.acl.IamContextFacade;
import pe.edu.upc.center.vitalia.shared.domain.events.FamilyMemberCreatedEvent;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.vitalia.users.domain.services.FamilyMemberCommandService;
import pe.edu.upc.center.vitalia.users.infrastructure.persistence.jpa.repositories.FamilyMemberRepository;

@Service
@Transactional
public class FamilyMemberCommandServiceImpl implements FamilyMemberCommandService {

    private final FamilyMemberRepository familyMemberRepository;
    private final IamContextFacade iamContextFacade;
    private final ApplicationEventPublisher publisher;

    public FamilyMemberCommandServiceImpl(FamilyMemberRepository familyMemberRepository,
                                          IamContextFacade iamContextFacade,
                                          ApplicationEventPublisher publisher) {
        this.familyMemberRepository = familyMemberRepository;
        this.iamContextFacade = iamContextFacade;
        this.publisher = publisher;
    }

    @Override
    public FamilyMember createFamilyMember(FamilyMember familyMember) {
      if (!iamContextFacade.existsByUserId(familyMember.getUserId().value())) {
        throw new IllegalArgumentException("User with id " + familyMember.getUserId().value() + " not found");
      }

      if (familyMemberRepository.existsByUserId(familyMember.getUserId())) {
        throw new IllegalArgumentException("Family member already exists with user id " + familyMember.getUserId().value());
      }

      var savedFamilyMember = familyMemberRepository.save(familyMember);
      var familyMemberEmail = iamContextFacade.fetchEmailByUserId(familyMember.getUserId().value());

      var event = new FamilyMemberCreatedEvent(
          savedFamilyMember.getRelationship(),
          savedFamilyMember.getLinkedResidentId(),
          savedFamilyMember.getFullName().getFirstName(),
          savedFamilyMember.getFullName().getLastName(),
          familyMemberEmail,
          savedFamilyMember.getUserId().value());
      publisher.publishEvent(event);

        return savedFamilyMember;
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
