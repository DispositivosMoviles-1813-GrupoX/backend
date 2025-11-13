package pe.edu.upc.center.vitalia.notification.application.internal.eventhandlers;

import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pe.edu.upc.center.vitalia.notification.application.external.emailservices.EmailService;
import pe.edu.upc.center.vitalia.shared.domain.events.UserCreatedEvent;

@Component
public class IamEventHandler {
  private final EmailService emailService;

  public IamEventHandler(EmailService emailService) {
    this.emailService = emailService;
  }

  @EventListener
  @Async
  public void handleUserCreatedEvent(UserCreatedEvent event) {
    try {
      System.out.println("📨 Evento recibido: nuevo usuario creado → " + event.username());

      emailService.sendWelcomeEmail(
          event.emailAddress(), // destinatario
          event.username(),     // nombre del usuario
          event.emailAddress()  // email para mostrar dentro del mensaje
      );

      System.out.println("Correo de bienvenida enviado a " + event.emailAddress());

    } catch (MessagingException e) {
      System.err.println("Error al enviar correo de bienvenida: " + e.getMessage());
    }
  }

}