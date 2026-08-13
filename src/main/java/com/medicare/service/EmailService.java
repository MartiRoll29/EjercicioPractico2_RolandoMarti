package com.medicare.service;

import com.medicare.domain.Usuario;

public interface EmailService {

    void enviarBienvenida(Usuario usuario);

    void enviarCorreo(String destinatario, String asunto, String cuerpo);
}
