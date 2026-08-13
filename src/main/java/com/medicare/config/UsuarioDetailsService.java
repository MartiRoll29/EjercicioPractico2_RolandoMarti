package com.medicare.config;

import com.medicare.domain.Usuario;
import com.medicare.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repositorioUsuario;

    public UsuarioDetailsService(UsuarioRepository repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario registrado = repositorioUsuario.findByEmail(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales no registradas: " + correo));

        String nombreRol = registrado.getRol() != null ? registrado.getRol().getNombre() : "PACIENTE";

        Collection<GrantedAuthority> permisos = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + nombreRol)
        );

        return User.withUsername(registrado.getEmail())
                .password(registrado.getPassword())
                .authorities(permisos)
                .disabled(Boolean.FALSE.equals(registrado.getActivo()))
                .build();
    }
}
