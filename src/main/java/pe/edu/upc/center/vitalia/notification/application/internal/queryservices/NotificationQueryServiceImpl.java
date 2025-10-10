package pe.edu.upc.center.vitalia.notification.application.internal.queryservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationQueryService;
import pe.edu.upc.center.vitalia.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getNotificationsByUserId(long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> searchNotifications(Long recipientId, String status) {
        if (recipientId != null && status != null) {
            return notificationRepository.findByRecipientIdAndStatus(recipientId, status);
        } else if (recipientId != null) {
            return notificationRepository.findByRecipientId(recipientId);
        } else if (status != null) {
            return notificationRepository.findByStatus(status);
        } else {
            return notificationRepository.findAll();
        }
    }
}