package pe.edu.upc.center.vitalia.users.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.users.domain.model.queries.GetFamilyMemberByResidentIdQuery;
import pe.edu.upc.center.vitalia.users.domain.services.FamilyMemberQueryService;

@Service
public class UserContextFacade {
  private final FamilyMemberQueryService familyMemberQueryService;

  public UserContextFacade(FamilyMemberQueryService familyMemberQueryService) {
    this.familyMemberQueryService = familyMemberQueryService;
  }

  public Long fetchFamilyMemberIdByResidentId(Long residentId) {
    var getFamilyMemberByResidentIdQuery = new GetFamilyMemberByResidentIdQuery(residentId);
    var familyMember = this.familyMemberQueryService.handle(getFamilyMemberByResidentIdQuery);
    if (familyMember.isEmpty()) return 0L;
    return familyMember.get().getLinkedResidentId();
  }

  public String fetchFamilyMemberNameByResidentId(Long residentId) {
    var getFamilyMemberByResidentIdQuery = new GetFamilyMemberByResidentIdQuery(residentId);
    var familyMember = this.familyMemberQueryService.handle(getFamilyMemberByResidentIdQuery);
    if (familyMember.isEmpty()) return "No se encontró el familiar";
    var fullName = familyMember.get().getFullName();
    return fullName.getFirstName() + " " + fullName.getLastName();
  }

  public Long fetchFamilyMemberUserIdByResidenId(Long residentId) {
    var getFamilyMemberByResidentIdQuery = new GetFamilyMemberByResidentIdQuery(residentId);
    var familyMember = this.familyMemberQueryService.handle(getFamilyMemberByResidentIdQuery);
    if (familyMember.isEmpty()) return 0L;
    return familyMember.get().getUserId().value();
  }
}
