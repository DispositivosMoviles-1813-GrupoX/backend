package pe.edu.upc.center.vitalia.notification.domain.services;

import pe.edu.upc.center.vitalia.notification.domain.model.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getAllNotifications();
    List<NotificationDTO> getNotificationsByUserId(String userId);
    List<NotificationDTO> searchNotifications(String status);
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    NotificationDTO markAsRead(String id);
    NotificationDTO archiveNotification(String id);
    NotificationDTO unarchiveNotification(String id);
    void deleteNotification(String id);
}