package pe.edu.upc.center.vitalia.appointments.application.internal.outboundservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.iam.interfaces.acl.IamContextFacade;

@Service("externalIamServiceAppointments")
public class ExternalIamService {
  private final IamContextFacade iamContextFacade;

  public ExternalIamService(IamContextFacade iamContextFacade) {
    this.iamContextFacade = iamContextFacade;
  }

  public String fetchEmailByUserId(Long userId) {
    return iamContextFacade.fetchEmailByUserId( userId);
  }
}
