package pe.edu.upc.center.vitalia.notification.interfaces.transform;

import pe.edu.upc.center.vitalia.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.vitalia.notification.interfaces.resources.CreateNotificationResource;

public class CreateNotificationCommandFromResourceAssembler {
  public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
    return new CreateNotificationCommand(
        resource.title(),
        resource.content(),
        resource.userId()
    );
  }
}