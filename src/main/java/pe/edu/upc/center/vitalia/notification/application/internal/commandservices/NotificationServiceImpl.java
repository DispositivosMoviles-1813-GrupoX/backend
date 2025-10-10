package pe.edu.upc.center.vitalia.notification.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.model.dto.NotificationDTO;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationService;
import pe.edu.upc.center.vitalia.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                            .map(this::toDTO)
                            .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getNotificationsByUserId(String userId) {
        List<Notification> notifications = notificationRepository.findByUserId(Long.parseLong(userId));
        return notifications.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> searchNotifications(String status) {
        List<Notification> notifications = notificationRepository.findByStatus(status);
        return notifications.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        if (notificationDTO == null) {
            throw new IllegalArgumentException("NotificationDTO cannot be null");
        }

        Notification notification = new Notification();
        notification.setTitle(notificationDTO.getTitle());
        notification.setContent(notificationDTO.getContent());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        notification.setTimestamp(LocalDateTime.now());
        notification.setStatus("unread");

        // Set the type field with a non-null value
        notification.setType("defaultType"); // Replace "defaultType" with the appropriate value

        try {
            notification.setUserId(Long.parseLong(notificationDTO.getUserId()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID format", e);
        }

        notificationRepository.save(notification);
        return toDTO(notification);
    }

    @Override
    public NotificationDTO markAsRead(String id) {
        Notification notification = notificationRepository.findById(Long.parseLong(id))
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("READ"); // Cambia el estado a leído
        notificationRepository.save(notification);
        return toDTO(notification);
    }

    @Override
    public NotificationDTO archiveNotification(String id) {
        Notification notification = notificationRepository.findById(Long.parseLong(id))
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("ARCHIVED"); // Cambia el estado a archivado
        notificationRepository.save(notification);
        return toDTO(notification);
    }

    @Override
    public NotificationDTO unarchiveNotification(String id) {
        Notification notification = notificationRepository.findById(Long.parseLong(id))
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("READ"); // Cambia el estado a "READ"
        notificationRepository.save(notification);
        return toDTO(notification);
    }

    @Override
    public void deleteNotification(String notificationId) {
        if (notificationId == null || notificationId.isEmpty()) {
            throw new IllegalArgumentException("Notification ID cannot be null or empty");
        }

        try {
            Long id = Long.parseLong(notificationId);
            notificationRepository.deleteById(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid notification ID format", e);
        }
    }

    private NotificationDTO toDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId() != null ? notification.getId().toString() : null);
        dto.setTitle(notification.getTitle());
        dto.setContent(notification.getContent());
        dto.setCreatedAt(notification.getCreatedAt() != null ? notification.getCreatedAt().toString() : null);
        dto.setStatus(notification.getStatus());
        dto.setType(notification.getType());
        dto.setUserId(notification.getUserId() != null ? notification.getUserId().toString() : null);

        return dto;
    }
}