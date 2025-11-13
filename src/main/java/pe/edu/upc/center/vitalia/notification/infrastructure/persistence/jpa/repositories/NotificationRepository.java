package pe.edu.upc.center.vitalia.notification.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.model.valueobjects.NotificationStatus;
import pe.edu.upc.center.vitalia.notification.domain.model.valueobjects.UserId;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  boolean existsById(Long id);
  List<Notification> findByUserId(UserId userId);
  List<Notification> findByNotificationStatus(NotificationStatus notificationStatus);
  List<Notification> findByUserIdAndNotificationStatus(UserId userId, NotificationStatus notificationStatus);
}