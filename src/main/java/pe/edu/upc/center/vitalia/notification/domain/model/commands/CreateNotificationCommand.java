package pe.edu.upc.center.vitalia.notification.domain.model.commands;

public record CreateNotificationCommand(String title,
                                        String content,
                                        Long userId) {

}