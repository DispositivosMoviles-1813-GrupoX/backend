package pe.edu.upc.center.vitalia.notification.interfaces.resources;

public record CreateNotificationResource(String title,
                                         String content,
                                         Long userId) {}