package pe.edu.upc.center.vitalia.notification.application.external.emailservices;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Service
public class EmailService {

  private final TemplateEngine templateEngine;

  @Value("${SENDGRID_API_KEY}")
  private String apiKey;

  @Value("${EMAIL}")
  private String from;

  public EmailService(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  // ============================================================
  // 📧 Método general para enviar correo con SendGrid API
  // ============================================================
  private void sendEmailApi(String to, String subject, String htmlContent) throws IOException {

    Email fromEmail = new Email(from, "MiApp");
    Email toEmail = new Email(to);

    Content content = new Content("text/html", htmlContent);
    Mail mail = new Mail(fromEmail, subject, toEmail, content);

    SendGrid sg = new SendGrid(apiKey);
    Request request = new Request();

    request.setMethod(Method.POST);
    request.setEndpoint("mail/send");
    request.setBody(mail.build());

    Response response = sg.api(request);

    System.out.println("📤 SendGrid status: " + response.getStatusCode());
    System.out.println("📤 Body: " + response.getBody());
  }

  // ============================================================
  // 📨 1. Email de bienvenida
  // ============================================================
  public void sendWelcomeEmail(String to, String username, String email) throws IOException {

    Context context = new Context();
    context.setVariable("username", username);
    context.setVariable("email", email);
    context.setVariable("urlLogin", "https://miapp.com/login");

    String templateName = "email/bienvenida-usuario";
    String html = templateEngine.process(templateName, context);

    String subject = "¡Bienvenido a MiApp, " + username + "! 🎉";

    sendEmailApi(to, subject, html);

    System.out.println("✅ Correo de bienvenida enviado a " + to);
  }

  // ============================================================
  // 📨 2. Email de doctor creado
  // ============================================================
  public void sendDoctorCreatedEmail(
      String to,
      String firstname,
      String lastname,
      String speciality,
      String licenseNumber,
      Long doctorId
  ) throws IOException {

    Context context = new Context();
    context.setVariable("firstName", firstname);
    context.setVariable("lastName", lastname);
    context.setVariable("specialty", speciality);
    context.setVariable("licenseNumber", licenseNumber);
    context.setVariable("doctorId", doctorId.toString());

    String templateName = "email/doctor-creado";
    String html = templateEngine.process(templateName, context);

    String subject = "Nuevo Doctor Registrado: Dr. " + firstname + " " + lastname;

    sendEmailApi(to, subject, html);

    System.out.println("✅ Correo de registro de doctor enviado a " + to);
  }

  // ============================================================
  // 📨 3. Email de familiar creado
  // ============================================================
  public void sendFamilyMemberCreatedEmail(
      String to,
      String relationship,
      String firstname,
      String lastname) throws IOException {

    Context context = new Context();
    context.setVariable("relationship", relationship);
    context.setVariable("firstName", firstname);
    context.setVariable("lastName", lastname);
    context.setVariable("email", to);

    String templateName = "email/familiar-creado";
    String html = templateEngine.process(templateName, context);

    String subject = "Nuevo Familiar Registrado: " + firstname + " " + lastname;

    sendEmailApi(to, subject, html);
    System.out.println("✅ Correo de registro de familiar enviado a " + to);
  }
}