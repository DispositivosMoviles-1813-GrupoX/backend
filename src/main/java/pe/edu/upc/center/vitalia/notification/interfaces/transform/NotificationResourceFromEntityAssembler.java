package pe.edu.upc.center.vitalia.notification.interfaces.transform;

import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.interfaces.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
  public static NotificationResource toResourceFromEntity(Notification entity) {
    var userId = entity.getUserId().userId();
    var status = entity.getNotificationStatus().toString();

    return new NotificationResource(
        entity.getId(),
        entity.getTitle(),
        entity.getContent(),
        status,
        userId);
  }
}