package pe.edu.upc.center.vitalia.appointments.application.internal.outboundservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.users.interfaces.acl.UserContextFacade;

@Service("externalUsersServiceAppointments")
public class ExternalUsersService {
  private final UserContextFacade userContextFacade;

  public ExternalUsersService(UserContextFacade userContextFacade) {
    this.userContextFacade = userContextFacade;
  }

  public Long fetchDoctorUserIdByDoctorId(Long doctorId) {
    return userContextFacade.fetchDoctorUserIdByDoctorId(doctorId);
  }
}
