package pe.edu.upc.center.vitalia.notification.domain.services;

import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.*;

import java.util.Optional;

public interface NotificationCommandService {
  Optional<Notification> handle(CreateNotificationCommand command);
  Optional<Notification> handle(UpdateNotificationCommand command);
  void handle(DeleteNotificationCommand command);
  Optional<Notification> handle(UpdateStatusToReadCommand command);
  Optional<Notification> handle(UpdateStatusToArchivedCommand command);
  Optional<Notification> alert(CreateNotificationCommand command);
}