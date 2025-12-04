package pe.edu.upc.center.vitalia.residents.application.internal.outboundservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.users.interfaces.acl.UserContextFacade;

@Service("externalUsersServiceResidents")
public class ExternalUsersService {
  private final UserContextFacade userContextFacade;

  public ExternalUsersService(UserContextFacade userContextFacade) {
    this.userContextFacade = userContextFacade;
  }

  public Long fetchFamilyMemberIdByResidentId(Long residentId) {
    return userContextFacade.fetchFamilyMemberIdByResidentId(residentId);
  }

  public String fetchFamilyMemberNameByResidentId(Long residentId) {
    return userContextFacade.fetchFamilyMemberNameByResidentId(residentId);
  }

  public Long fetchFamilyMemberUserIdByResidenId(Long residentId) {
    return userContextFacade.fetchFamilyMemberUserIdByResidenId(residentId);
  }
}
