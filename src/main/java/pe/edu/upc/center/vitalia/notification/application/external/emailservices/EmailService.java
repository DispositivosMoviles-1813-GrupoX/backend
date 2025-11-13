package pe.edu.upc.center.vitalia.notification.application.external.emailservices;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  public EmailService(JavaMailSender mailSender,
                      TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  /**
   * Envía un correo de bienvenida usando la plantilla Thymeleaf.
   */
  public void sendWelcomeEmail(String to, String username, String email) throws MessagingException {
    // Crear el contexto Thymeleaf (variables para la plantilla)
    Context context = new Context();
    context.setVariable("username", username);
    context.setVariable("email", email);
    context.setVariable("urlLogin", "https://miapp.com/login");

    // Nombre de la plantilla (ruta relativa a /resources/templates/)
    String templateName = "email/bienvenida-usuario";

    // Procesar plantilla y generar el HTML final
    String htmlContent = templateEngine.process(templateName, context);

    // Configurar mensaje MIME
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

    helper.setTo(to);
    helper.setSubject("¡Bienvenido a MiApp, " + username + "! 🎉");
    helper.setText(htmlContent, true); // Activar HTML

    // Enviar correo
    mailSender.send(mimeMessage);

    System.out.println("✅ Correo de bienvenida enviado a " + to);
  }
}