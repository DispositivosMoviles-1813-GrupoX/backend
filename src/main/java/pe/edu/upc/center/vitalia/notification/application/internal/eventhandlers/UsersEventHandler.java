package pe.edu.upc.center.vitalia.notification.application.internal.eventhandlers;

import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pe.edu.upc.center.vitalia.notification.application.external.emailservices.EmailService;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationCommandService;
import pe.edu.upc.center.vitalia.shared.domain.events.DoctorCreatedEvent;

import java.io.IOException;

@Component
public class UsersEventHandler {
  private final EmailService emailService;
  private final NotificationCommandService notificationCommandService;

  public UsersEventHandler(EmailService emailService,
                           NotificationCommandService notificationCommandService) {
    this.emailService = emailService;
    this.notificationCommandService = notificationCommandService;
  }

  @EventListener
  @Async
  public void handleDoctorCreatedEvent(DoctorCreatedEvent event){
    try {
      emailService.sendDoctorCreatedEmail(
          event.emailAddress(),
          event.firstName(),
          event.lastName(),
          event.specialty(),
          event.licenseNumber(),
          event.doctorId()
      );

      var createNotificationCommand = getCreateNotificationCommandByDoctor(event);
      var result = notificationCommandService.handle(createNotificationCommand);

      if (result.isPresent()) {
        System.out.println(" Notificación creada exitosamente: " + result.get().getId());
      } else {
        System.err.println(" No se pudo crear la notificación");
      }

    } catch (IOException e) {
      System.err.println("Error al enviar correo de bienvenida: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al crear notificación: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private CreateNotificationCommand getCreateNotificationCommandByDoctor(DoctorCreatedEvent event) {
    // Construyendo un título simple
    String title = "Nuevo Doctor Registrado: " + event.firstName() + " " + event.lastName();

    // Construyendo un contenido simple
    String content = String.format(
        "El Doctor %s %s ha sido registrado con éxito. Especialidad: %s. Número de Licencia: %s.",
        event.firstName(),
        event.lastName(),
        event.specialty(),
        event.licenseNumber()
    );

    return new CreateNotificationCommand(title, content, 1L);

  }
}
