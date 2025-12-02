package pe.edu.upc.center.vitalia.users.application.internal.outboundservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.iam.interfaces.acl.IamContextFacade;

@Service("externalIamServiceUsers")
public class ExternalIamService {
  private final IamContextFacade iamContextFacade;

  public ExternalIamService(IamContextFacade iamContextFacade) {
    this.iamContextFacade = iamContextFacade;
  }

  public String getDoctorEmail(Long userId) {
    return iamContextFacade.fetchEmailByUserId( userId);
  }

  public boolean existsByUserId(Long userId) {
    return iamContextFacade.existsByUserId(userId);
  }

  public Long fetchUserIdByEmail(String email) {
    return iamContextFacade.fetchUserIdByEmail(email);
  }
}
