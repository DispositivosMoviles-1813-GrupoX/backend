package pe.edu.upc.center.vitalia.notification.domain.model.queries;

public record GetNotificationsByUserIdAndStatusQuery(Long userId, String status) {
}
