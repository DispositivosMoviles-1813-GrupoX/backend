package pe.edu.upc.center.vitalia.notification.interfaces.resources;

public record NotificationResource(Long id,
                                   String title,
                                   String content,
                                   String status,
                                   Long userId) {
}