package pe.edu.upc.center.vitalia.notification.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationService;
import pe.edu.upc.center.vitalia.notification.domain.model.dto.NotificationDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "Endpoints for managing notifications")
@CrossOrigin(origins = "https://8093") // Cambia esto por los orígenes permitidos
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @Operation(summary = "List all notifications", description = "Retrieve a list of all notifications")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/search")
    @Operation(summary = "Filter notifications by status", description = "Retrieve notifications filtered by status (read, unread, archived)")
    public List<NotificationDTO> searchNotifications(@RequestParam String status) {
        return notificationService.searchNotifications(status);
    }

    @GetMapping("/notifications/{userId}")
    @Operation(summary = "List notifications by user", description = "Retrieve notifications for a specific user by user ID")
    public List<NotificationDTO> getNotificationsByUserId(@PathVariable String userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    @PostMapping
    @Operation(summary = "Create a notification", description = "Create a new notification")
    public NotificationDTO createNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationService.createNotification(notificationDTO);
    }

    @PostMapping("/{id}/mark-as-read")
    @Operation(summary = "Mark notification as read", description = "Mark a specific notification as read by its ID")
    public NotificationDTO markAsRead(@PathVariable String id) {
        return notificationService.markAsRead(id);
    }

    @PostMapping("/{id}/archive")
    @Operation(summary = "Archive a notification", description = "Archive a specific notification by its ID")
    public NotificationDTO archiveNotification(@PathVariable String id) {
        return notificationService.archiveNotification(id);
    }

    @PostMapping("/{id}/unarchive")
    @Operation(summary = "Unarchive a notification", description = "Unarchive a specific notification by its ID, changing its status to 'read'")
    public NotificationDTO unarchiveNotification(@PathVariable String id) {
        return notificationService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification", description = "Delete a specific notification by its ID")
    public void deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
    }
}