package com.medicare.serviceimpl;

import com.medicare.domain.Usuario;
import com.medicare.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarBienvenida(Usuario usuario) {
        try {
            String asunto = "Bienvenido a MediCare";
            String cuerpo = "Estimado/a " + usuario.getNombre() + ",\n\n" +
                    "¡Bienvenido/a a MediCare!\n\n" +
                    "Tu cuenta ha sido creada exitosamente con el siguiente rol: " + usuario.getRol().getNombre() + "\n" +
                    "Email: " + usuario.getEmail() + "\n\n" +
                    "Puedes acceder a la plataforma con tus credenciales.\n\n" +
                    "Saludos cordiales,\n" +
                    "El equipo de MediCare";

            enviarCorreo(usuario.getEmail(), asunto, cuerpo);
        } catch (Exception e) {
            System.err.println("Error al enviar correo de bienvenida: " + e.getMessage());
        }
    }

    @Override
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("noreply@medicare.com");
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            mailSender.send(mensaje);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
