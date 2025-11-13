package pe.edu.upc.center.vitalia.notification.domain.model.exceptions;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(Long id) {
        super("Notification with id " + id + " not found.");
    }
}
