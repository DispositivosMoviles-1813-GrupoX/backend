package pe.edu.upc.center.vitalia.notification.application.internal.eventhandlers;

import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pe.edu.upc.center.vitalia.notification.application.external.emailservices.EmailService;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationCommandService;
import pe.edu.upc.center.vitalia.shared.domain.events.AddedScheduled;
import pe.edu.upc.center.vitalia.shared.domain.events.DoctorCreatedEvent;
import pe.edu.upc.center.vitalia.shared.domain.events.FamilyMemberCreatedEvent;

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

  // ============================================================
  // 📨 1. Evento de doctor creado
  // ============================================================
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

    return new CreateNotificationCommand(title, content, event.userId());

  }

  // ============================================================
  // 📨 2. Evento de familiar creado
  // ============================================================
  @EventListener
  @Async
  public void handleFamilyMemberCreatedEvent(FamilyMemberCreatedEvent event){
    try {
      emailService.sendFamilyMemberCreatedEmail(
          event.emailAddress(),
          event.relationship(),
          event.firstName(),
          event.lastName()
      );

      var createNotificationCommand = getCreateNotificationCommandByFamilyMember(event);
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

  private CreateNotificationCommand getCreateNotificationCommandByFamilyMember(FamilyMemberCreatedEvent event) {
    // Construyendo un título simple
    String title = "Nuevo Familiar Registrado: " + event.firstName() + " " + event.lastName();

    // Construyendo un contenido simple
    String content = String.format(
        "El Familiar %s %s ha sido registrado con éxito. Relación con el residente: %s.",
        event.firstName(),
        event.lastName(),
        event.relationship()
    );

    return new CreateNotificationCommand(title, content, event.userId());
  }

  // ============================================================
  // 📨 3. Evento de horario añadido
  // ============================================================
  @EventListener
  @Async
  public void handleAddedSchedule(AddedScheduled event) {
    try {

      // Envío del correo usando el nuevo método
      emailService.sendScheduleAddedEmail(event);

      // Crear notificación
      var createNotificationCommand = getCreateNotificationCommandByAddedSchedule(event);
      var result = notificationCommandService.handle(createNotificationCommand);

      if (result.isPresent()) {
        System.out.println(" Notificación de horario creada exitosamente: " + result.get().getId());
      } else {
        System.err.println(" No se pudo crear la notificación de horario");
      }

    } catch (IOException e) {
      System.err.println("Error al enviar correo de horario añadido: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al crear notificación de horario añadido: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private CreateNotificationCommand getCreateNotificationCommandByAddedSchedule(AddedScheduled event) {

    String title = "Nuevo horario añadido para el Doctor (ID: " + event.doctorId() + ")";

    String content = String.format(
        "Se ha registrado un nuevo horario para el doctor con ID %d. "
            + "Día: %s. Hora de inicio: %s. Hora de fin: %s. ID de la cita generada: %d.",
        event.doctorId(),
        event.day(),
        event.startTime(),
        event.endTime(),
        event.appointmentId()
    );

    return new CreateNotificationCommand(title, content, event.userId());
  }
}
