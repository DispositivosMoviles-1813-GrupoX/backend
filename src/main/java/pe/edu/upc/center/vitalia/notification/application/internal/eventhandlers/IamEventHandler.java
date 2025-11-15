package pe.edu.upc.center.vitalia.notification.application.internal.eventhandlers;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import pe.edu.upc.center.vitalia.notification.application.external.emailservices.EmailService;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.services.NotificationCommandService;
import pe.edu.upc.center.vitalia.shared.domain.events.UserCreatedEvent;

import java.util.List;

@Component
public class IamEventHandler {
  private final EmailService emailService;
  private final NotificationCommandService notificationCommandService;

  public IamEventHandler(EmailService emailService,
                         NotificationCommandService notificationCommandService) {
    this.emailService = emailService;
    this.notificationCommandService = notificationCommandService;
  }

  @PostConstruct
  public void init() {
    System.out.println("📩 IamEventHandler inicializado correctamente");
  }

  @EventListener
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleUserCreatedEvent(UserCreatedEvent event) {
    try {
      // Enviar email
      emailService.sendWelcomeEmail(
          event.emailAddress(),
          event.username(),
          event.emailAddress()
      );

      // Crear notificación personalizada por rol
      var createNotificationCommand = getCreateNotificationCommandByRole(event);
      var result = notificationCommandService.handle(createNotificationCommand);

      if (result.isPresent()) {
        System.out.println(" Notificación creada exitosamente: " + result.get().getId());
      } else {
        System.err.println(" No se pudo crear la notificación");
      }

    } catch (MessagingException e) {
      System.err.println("Error al enviar correo de bienvenida: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al crear notificación: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static CreateNotificationCommand getCreateNotificationCommandByRole(UserCreatedEvent event) {
    // Determinar el rol principal (puede tener múltiples roles)
    String userRole = determinePrimaryRole(event.roles());

    return switch (userRole) {
      case "ROLE_DOCTOR" -> createDoctorNotification(event);
      case "ROLE_RESIDENT" -> createResidentNotification(event);
      case "ROLE_FAMILY" -> createFamilyNotification(event);
      default -> createDefaultNotification(event);
    };
  }

  private static String determinePrimaryRole(List<String> roles) {
    if (roles == null || roles.isEmpty()) {
      return "DEFAULT";
    }

    // Prioridad de roles para determinar la notificación principal
    if (roles.contains("ROLE_DOCTOR")) return "ROLE_DOCTOR";
    if (roles.contains("ROLE_RESIDENT")) return "ROLE_RESIDENT";
    if (roles.contains("ROLE_FAMILY")) return "ROLE_FAMILY";

    return roles.getFirst(); // Retorna el primer rol si no coincide con los principales
  }

  private static CreateNotificationCommand createDoctorNotification(UserCreatedEvent event) {
    String title = "👨‍⚕️ ¡Bienvenido Dr./Dra. " + event.username() + " a Vitalia!";
    String content = """
            Estimado/a Colega,
            
            Su cuenta profesional en Vitalia ha sido activada exitosamente.
            
            **Ahora puede:**
            • Gestionar residentes asignados
            • Supervisar casos médicos
            • Proporcionar orientación especializada
            • Colaborar con familiares
            • Acceder a recursos médicos exclusivos
            
            Correo verificado: %s
            ID Profesional: %d
            
            Su experiencia es invaluable para nuestra comunidad médica.
            """.formatted(event.emailAddress(), event.userId());

    return new CreateNotificationCommand(title, content, event.userId());
  }

  private static CreateNotificationCommand createResidentNotification(UserCreatedEvent event) {
    String title = "🎓 ¡Bienvenido/a Residente " + event.username() + "!";
    String content = """
            ¡Su journey médico comienza ahora!
            
            **Con Vitalia podrá:**
            • Conectar con sus doctores supervisores
            • Registrar sus progresos y casos
            • Recibir feedback especializado
            • Coordinar con el equipo médico
            • Acceder a materiales de formación
            
            Estamos aquí para apoyar su crecimiento profesional.
            
            Correo registrado: %s
            ID de Residente: %d
            """.formatted(event.emailAddress(), event.userId());

    return new CreateNotificationCommand(title, content, event.userId());
  }

  private static CreateNotificationCommand createFamilyNotification(UserCreatedEvent event) {
    String title = "❤️ ¡Bienvenido/a a la Familia Vitalia!";
    String content = """
            Estimado/a %s,
            
            Gracias por unirse a Vitalia. Ahora forma parte de la red de apoyo 
            de nuestros profesionales médicos.
            
            **Podrá:**
            • Mantenerse informado sobre el progreso
            • Comunicarse con el equipo médico
            • Recibir actualizaciones importantes
            • Acceder a recursos de apoyo
            
            Su participación es fundamental en este proceso.
            
            Correo de contacto: %s
            ID de Usuario: %d
            """.formatted(event.username(), event.emailAddress(), event.userId());

    return new CreateNotificationCommand(title, content, event.userId());
  }

  private static CreateNotificationCommand createDefaultNotification(UserCreatedEvent event) {
    String title = "🏥 ¡Bienvenido/a a Vitalia, " + event.username() + "!";
    String content = """
            Su cuenta ha sido creada exitosamente.
            
            **Información de su cuenta:**
            📧 Email: %s
            🆔 ID: %d
            👥 Rol: %s
            
            Estamos aquí para apoyarle en su experiencia médica.
            """.formatted(event.emailAddress(), event.userId(), event.roles());

    return new CreateNotificationCommand(title, content, event.userId());
  }

}