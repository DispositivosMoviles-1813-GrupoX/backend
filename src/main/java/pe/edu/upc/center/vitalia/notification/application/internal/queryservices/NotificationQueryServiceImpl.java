package pe.edu.upc.center.vitalia.notification.application.internal.queryservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.model.queries.*;
import pe.edu.upc.center.vitalia.notification.domain.model.valueobjects.NotificationStatus;
import pe.edu.upc.center.vitalia.notification.domain.model.valueobjects.UserId;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationQueryService;
import pe.edu.upc.center.vitalia.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

  private final NotificationRepository notificationRepository;

  public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  @Override
  public List<Notification> handle(GetAllNotificationsQuery query) {
    return notificationRepository.findAll();
  }

  @Override
  public Optional<Notification> handle(GetNotificationByIdQuery query) {
    return notificationRepository.findById(query.notificationId());
  }

  @Override
  public List<Notification> handle(GetNotificationsByUserIdQuery query) {
    var userId = new UserId(query.userId());
    return notificationRepository.findByUserId(userId);
  }

  @Override
  public List<Notification> handle(GetNotificationsByStatus query) {
    var status = NotificationStatus.valueOf(query.status().toUpperCase());
    return notificationRepository.findByNotificationStatus(status);
  }

  @Override
  public List<Notification> handle(GetNotificationsByUserIdAndStatusQuery query) {
    var userId = new UserId(query.userId());
    var status = NotificationStatus.valueOf(query.status().toUpperCase());

    return notificationRepository.findByUserIdAndNotificationStatus(userId, status);
  }
}