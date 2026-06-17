package com.tpi.gpdrl.Seguridad.Clase;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class envioCorreo {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine; // Thymeleaf template engine

    public void sendEmail(String subject, String descripcion, String usuarioCorreo, String nuevaContrasena, String consideracion) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            // Procesar la plantilla Thymeleaf
            Context context = new Context();
            context.setVariable("titulo", subject);
            context.setVariable("nombre", usuarioCorreo);
            context.setVariable("contrasena", nuevaContrasena);
            context.setVariable("descripcion", descripcion);
            context.setVariable("consideracion", consideracion);
            String htmlContent = templateEngine.process("/Seguridad/Recuperacion.html", context);

            // Configurar el correo
            helper.setTo(usuarioCorreo);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true indica que el contenido es HTML
            mailSender.send(message);
            System.err.println("Mensaje enviado");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}