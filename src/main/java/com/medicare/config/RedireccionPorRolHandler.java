package com.medicare.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class RedireccionPorRolHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest peticion,
                                        HttpServletResponse respuesta,
                                        Authentication autenticacion) throws IOException, ServletException {

        Set<String> permisos = autenticacion.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String destino;
        if (permisos.contains("ROLE_ADMIN")) {
            destino = "/admin/usuarios";
        } else if (permisos.contains("ROLE_MEDICO")) {
            destino = "/citas";
        } else if (permisos.contains("ROLE_PACIENTE")) {
            destino = "/citas";
        } else {
            destino = "/inicio";
        }

        respuesta.sendRedirect(peticion.getContextPath() + destino);
    }
}
