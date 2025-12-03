package pe.edu.upc.center.vitalia.notification.application.internal.eventhandlers;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pe.edu.upc.center.vitalia.notification.application.external.emailservices.EmailService;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationCommandService;
import pe.edu.upc.center.vitalia.shared.domain.events.ResidentCreatedEvent;

import java.io.IOException;

@Component
public class ResidentsEventHandler {
  private final EmailService emailService;
  private final NotificationCommandService notificationCommandService;

  public ResidentsEventHandler(EmailService emailService,
                               NotificationCommandService notificationCommandService) {
    this.emailService = emailService;
    this.notificationCommandService = notificationCommandService;
  }

  // ============================================================
  // 1. Evento de residente creado
  // ============================================================
  @EventListener
  @Async
  public void handleResidentCreatedEvent(ResidentCreatedEvent event) {
    try {
      // 1. Envío de correo al familiar responsable
      emailService.sendResidentCreatedEmail(event);

      // 2️. Crear notificación en la plataforma
      var createNotificationCommand = getCreateNotificationCommandByResident(event);
      var result = notificationCommandService.handle(createNotificationCommand);

      if (result.isPresent()) {
        System.out.println("Notificación de residente creada exitosamente: " + result.get().getId());
      } else {
        System.err.println("No se pudo crear la notificación de residente");
      }

    } catch (IOException e) {
      System.err.println("Error al enviar correo de residente creado: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al crear notificación de residente: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private CreateNotificationCommand getCreateNotificationCommandByResident(ResidentCreatedEvent event) {

    String title = "Nuevo Residente Registrado en Vitalia: " +
        event.firstName() + " " + event.lastName();

    String content = String.format(
        "Se ha registrado un nuevo residente en Vitalia.\n" +
            "Nombre: %s %s\n" +
            "DNI: %s\n" +
            "Género: %s\n" +
            "Ciudad: %s, País: %s\n" +
            "Familiar responsable: %s (ID: %d, UserID: %d)",
        event.firstName(),
        event.lastName(),
        event.dni(),
        event.gender(),
        event.city(),
        event.country(),
        event.familyMemberName(),
        event.familyMemberId(),
        event.familyMemberUserId()
    );

    // La notificación se asigna al familiar responsable
    return new CreateNotificationCommand(title, content, event.familyMemberUserId());
  }
}
