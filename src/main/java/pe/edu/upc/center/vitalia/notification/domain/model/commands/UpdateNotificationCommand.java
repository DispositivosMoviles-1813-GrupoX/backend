package pe.edu.upc.center.vitalia.notification.domain.model.commands;

public record UpdateNotificationCommand(Long notificationId,
                                        String title,
                                        String content,
                                        String status) {

}