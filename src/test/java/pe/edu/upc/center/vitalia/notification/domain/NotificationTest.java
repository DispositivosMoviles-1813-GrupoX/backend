package pe.edu.upc.center.vitalia.notification.domain;

import org.junit.jupiter.api.Test;
import pe.edu.upc.center.vitalia.notification.domain.model.aggregates.Notification;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void testNotificationCreationWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification(
                1L,
                "Recordatorio de medicación",
                "Debe tomar su medicamento a las 8:00 am",
                now,
                now,
                "unread",
                10L,
                20L,
                "reminder"
        );

        assertEquals(1L, notification.getId());
        assertEquals("Recordatorio de medicación", notification.getTitle());
        assertEquals("Debe tomar su medicamento a las 8:00 am", notification.getContent());
        assertEquals("unread", notification.getStatus());
        assertEquals(10L, notification.getUserId());
        assertEquals(20L, notification.getRecipientId());
        assertEquals("reminder", notification.getType());
        assertNotNull(notification.getTimestamp());
    }

    @Test
    void testNotificationCreationWithSimplifiedConstructor() {
        Notification notification = new Notification(
                "Cita médica",
                "Tiene una cita con el doctor a las 10:00 am",
                5L,
                8L,
                "appointment"
        );

        assertEquals("Cita médica", notification.getTitle());
        assertEquals("Tiene una cita con el doctor a las 10:00 am", notification.getContent());
        assertEquals("unread", notification.getStatus());
        assertEquals(5L, notification.getUserId());
        assertEquals(8L, notification.getRecipientId());
        assertEquals("appointment", notification.getType());
        assertNotNull(notification.getCreatedAt());
        assertNotNull(notification.getTimestamp());
    }
}
