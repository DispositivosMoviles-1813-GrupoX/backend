package pe.edu.upc.center.vitalia.notification.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.DeleteNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.UpdateStatusToArchivedCommand;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.UpdateStatusToReadCommand;
import pe.edu.upc.center.vitalia.notification.domain.model.queries.GetAllNotificationsQuery;
import pe.edu.upc.center.vitalia.notification.domain.model.queries.GetNotificationsByStatus;
import pe.edu.upc.center.vitalia.notification.domain.model.queries.GetNotificationsByUserIdAndStatusQuery;
import pe.edu.upc.center.vitalia.notification.domain.model.queries.GetNotificationsByUserIdQuery;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationCommandService;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationQueryService;
import pe.edu.upc.center.vitalia.notification.interfaces.resources.CreateNotificationResource;
import pe.edu.upc.center.vitalia.notification.interfaces.resources.NotificationResource;
import pe.edu.upc.center.vitalia.notification.interfaces.transform.CreateNotificationCommandFromResourceAssembler;
import pe.edu.upc.center.vitalia.notification.interfaces.transform.NotificationResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "Endpoints for managing notifications")
@CrossOrigin(origins = "https://8093") // Cambia esto por los orígenes permitidos
public class NotificationController {

  private final NotificationCommandService notificationCommandService;
  private final NotificationQueryService notificationQueryService;

  public NotificationController(NotificationCommandService notificationCommandService,
                                NotificationQueryService notificationQueryService) {
    this.notificationCommandService = notificationCommandService;
    this.notificationQueryService = notificationQueryService;
  }

  @GetMapping
  @Operation(summary = "List all notifications", description = "Retrieve a list of all notifications")
  public ResponseEntity<List<NotificationResource>> getAllNotifications() {
    var getAllNotificationsQuery = new GetAllNotificationsQuery();
    var notifications = notificationQueryService.handle(getAllNotificationsQuery);
    var notificationsResources = notifications.stream()
        .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
        .collect(Collectors.toList());
    return ResponseEntity.ok(notificationsResources);
  }

  @GetMapping("/search")
  @Operation(summary = "Filter notifications by status", description = "Retrieve notifications filtered by status (read, unread, archived)")
  public ResponseEntity<List<NotificationResource>> searchNotifications(@RequestParam String status) {
    var getNotificationsByStatusQuery = new GetNotificationsByStatus(status);
    var notifications = notificationQueryService.handle(getNotificationsByStatusQuery);
    var notificationsResources = notifications.stream()
        .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
        .collect(Collectors.toList());

    return ResponseEntity.ok(notificationsResources);
  }

  @GetMapping("/userId/{userId}")
  @Operation(summary = "List notifications by user", description = "Retrieve notifications for a specific user by user ID")
  public ResponseEntity<List<NotificationResource>> getNotificationsByUserId(@PathVariable Long userId) {
    var getNotificationsByUserIdQuery = new GetNotificationsByUserIdQuery(userId);
    var notifications = notificationQueryService.handle(getNotificationsByUserIdQuery);
    var notificationsResources = notifications.stream()
        .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
        .collect(Collectors.toList());

    return ResponseEntity.ok(notificationsResources);
  }

  @GetMapping("/userId/{userId}/status")
  @Operation(summary = "List notifications by user and status", description = "Retrieve notifications for a specific user by user ID and status")
  public ResponseEntity<List<NotificationResource>> getNotificationsByUserIdAndStatus(
      @PathVariable Long userId,
      @RequestParam String status) {
    var getNotificationsByUserIdAndStatusQuery = new GetNotificationsByUserIdAndStatusQuery(userId, status);
    var notifications = notificationQueryService.handle(getNotificationsByUserIdAndStatusQuery);
    var notificationsResources = notifications.stream()
        .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
        .collect(Collectors.toList());

    return ResponseEntity.ok(notificationsResources);
  }

  @PostMapping
  @Operation(summary = "Create a notification", description = "Create a new notification")
  public ResponseEntity<NotificationResource> createNotification(
      @RequestBody CreateNotificationResource resource) {
    var createNotificationCommand = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
    var notification = notificationCommandService.handle(createNotificationCommand);
    if (notification.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
    return new ResponseEntity<>(notificationResource, HttpStatus.CREATED);
  }

  @PostMapping("/{id}/mark-as-read")
  @Operation(summary = "Mark notification as read", description = "Mark a specific notification as read by its ID")
  public ResponseEntity<NotificationResource> markAsRead(@PathVariable Long id) {
    var updateStatusToReadCommand = new UpdateStatusToReadCommand(id);
    var notification = notificationCommandService.handle(updateStatusToReadCommand);
    if (notification.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());

    return new ResponseEntity<>(notificationResource, HttpStatus.OK);
  }

  @PostMapping("/{id}/archive")
  @Operation(summary = "Archive a notification", description = "Archive a specific notification by its ID")
  public ResponseEntity<NotificationResource> archiveNotification(@PathVariable Long id) {
    var updatedStatusToArchivedCommand = new UpdateStatusToArchivedCommand(id);
    var notification = notificationCommandService.handle(updatedStatusToArchivedCommand);
    if (notification.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());

    return new ResponseEntity<>(notificationResource, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a notification", description = "Delete a specific notification by its ID")
  public void deleteNotification(@PathVariable Long id) {
    var deleteNotificationCommand = new DeleteNotificationCommand(id);
    notificationCommandService.handle(deleteNotificationCommand);
  }
}