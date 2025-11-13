package pe.edu.upc.center.vitalia.notification.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.*;
import pe.edu.upc.center.vitalia.notification.domain.model.exceptions.NotificationNotFoundException;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationCommandService;
import pe.edu.upc.center.vitalia.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

  private final NotificationRepository notificationRepository;

  public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  @Override
  public Optional<Notification> handle(CreateNotificationCommand command) {

    var notification = new Notification(command);
    notificationRepository.save(notification);

    return Optional.of(notification);
  }

  @Override
  public Optional<Notification> handle(UpdateNotificationCommand command) {
    return Optional.empty();
  }

  @Override
  public void handle(DeleteNotificationCommand command) {
    var notificationId = command.notificationId();
    if (!notificationRepository.existsById(notificationId)) {
      throw new IllegalArgumentException("Notification with id " + notificationId + " does not exist");
    }

    notificationRepository.deleteById(notificationId);

  }

  @Override
  public Optional<Notification> handle(UpdateStatusToReadCommand command) {
    var notificationId = command.notificationId();
    var notification = notificationRepository.findById(notificationId);
    if (notification.isEmpty()) {
      throw new IllegalArgumentException("Notification with id " + notificationId + " does not exist");
    }

    var updatedNotification = notification.get();
    updatedNotification.markAsRead();
    notificationRepository.save(updatedNotification);

    return Optional.of(updatedNotification);
  }

  @Override
  public Optional<Notification> handle(UpdateStatusToArchivedCommand command) {
    var notificationId = command.notificationId();
    var notification = notificationRepository.findById(notificationId);
    if (notification.isEmpty()) {
      throw new IllegalArgumentException("Notification with id " + notificationId + " does not exist");
    }

    var updatedNotification = notification.get();
    updatedNotification.markAsArchived();
    notificationRepository.save(updatedNotification);

    return Optional.of(updatedNotification);
  }
}