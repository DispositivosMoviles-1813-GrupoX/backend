package pe.edu.upc.center.vitalia.notification.domain.services;

import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationQueryService {
    List<Notification> getAllNotifications();
    Notification getNotificationById(long id);
    Optional<Notification> getNotificationById(Long id);
    List<Notification> getNotificationsByUserId(long userId);
    List<Notification> searchNotifications(Long recipientId, String status);
}