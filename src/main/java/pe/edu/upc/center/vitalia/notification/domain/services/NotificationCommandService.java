package pe.edu.upc.center.vitalia.notification.domain.services;

import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.DeleteNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.UpdateNotificationCommand;

import java.util.Optional;

public interface NotificationCommandService {
  Optional<Notification> handle(CreateNotificationCommand command);
  Optional<Notification> handle(UpdateNotificationCommand command);
  void handle(DeleteNotificationCommand command);
}