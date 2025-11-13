package pe.edu.upc.center.vitalia.notification.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.model.exceptions.NotificationNotFoundException;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationCommandService;
import pe.edu.upc.center.vitalia.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;

import java.time.LocalDateTime;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(CreateNotificationCommand command) {
        Notification notification = new Notification();
        notification.setTitle(command.getTitle());
        notification.setContent(command.getContent());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        notification.setTimestamp(LocalDateTime.now()); // Ensure this line is present
        notification.setStatus("unread");
        notification.setUserId(command.getUserId());
        // Set other fields as necessary

        return notificationRepository.save(notification);
    }

    @Override
    public Notification deliverNotification(long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("DELIVERED"); // O el status que corresponda
        return notificationRepository.save(notification);
    }

    @Override
    public Notification archiveNotification(long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("ARCHIVED"); // O el status que corresponda
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new NotificationNotFoundException(id));
        notificationRepository.delete(notification);
    }
}