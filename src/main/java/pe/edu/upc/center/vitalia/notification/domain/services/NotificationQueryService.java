package pe.edu.upc.center.vitalia.notification.domain.services;

import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.model.queries.*;
import pe.edu.upc.center.vitalia.notification.domain.model.valueobjects.NotificationStatus;

import java.util.List;
import java.util.Optional;

public interface NotificationQueryService {
  List<Notification> handle(GetAllNotificationsQuery query);
  Optional<Notification> handle(GetNotificationByIdQuery query);
  List<Notification> handle(GetNotificationsByUserIdQuery query);
  List<Notification> handle(GetNotificationsByStatus query);
  List<Notification> handle(GetNotificationsByUserIdAndStatusQuery query);
}