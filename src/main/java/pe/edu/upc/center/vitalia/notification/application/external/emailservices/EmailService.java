package pe.edu.upc.center.vitalia.notification.application.external.emailservices;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @Value("${spring.mail.from}")
  private String from;

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
    helper.setFrom(from);

    // Enviar correo
    mailSender.send(mimeMessage);

    System.out.println("✅ Correo de bienvenida enviado a " + to);
  }

  public void sendDoctorCreatedEmail(String to,
                                     String firstname,
                                     String lastname,
                                     String speciality,
                                     String licenseNumber,
                                     Long doctorId) throws MessagingException {

    Context context = new Context();
    context.setVariable("firstName", firstname);
    context.setVariable("lastName", lastname);
    context.setVariable("specialty", speciality);
    context.setVariable("licenseNumber", licenseNumber);
    context.setVariable("doctorId", doctorId.toString());

    String templateName = "email/doctor-creado";
    String htmlContent = templateEngine.process(templateName, context);
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

    helper.setTo(to);
    helper.setSubject("Nuevo Doctor Registrado: Dr. " + firstname + " " + lastname);
    helper.setText(htmlContent, true); // Activar HTML
    helper.setFrom(from);

    mailSender.send(mimeMessage);
    System.out.println("✅ Correo de registro de doctor enviado a " + to +
        " (Doctor: " + firstname + " " + lastname + ")");

  }
}