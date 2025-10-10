package pe.edu.upc.center.vitalia.notification.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientId(Long recipientId); // Ensure this matches the entity field
    List<Notification> findByUserId(Long userId); // Add this method
    List<Notification> findByRecipientIdAndStatus(Long recipientId, String status); // Add this method
    List<Notification> findByStatus(String status); // Add this method
}