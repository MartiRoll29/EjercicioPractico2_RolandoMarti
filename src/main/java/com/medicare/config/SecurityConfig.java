package com.medicare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService detallesUsuarioService;

    public SecurityConfig(UsuarioDetailsService detallesUsuarioService) {
        this.detallesUsuarioService = detallesUsuarioService;
    }

    // Las credenciales oficiales del script del caso practico se almacenan en texto plano.
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder codificadorClaves() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain cadenaFiltrosSeguridad(HttpSecurity http) throws Exception {
        http
            .userDetailsService(detallesUsuarioService)
            .authorizeHttpRequests(peticiones -> peticiones
                .requestMatchers("/", "/inicio", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/citas/nuevo", "/citas/guardar").hasAnyRole("ADMIN", "MEDICO")
                .requestMatchers("/citas/editar/**", "/citas/eliminar/**").hasAnyRole("ADMIN", "MEDICO")
                .requestMatchers("/citas", "/citas/detalle/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                .requestMatchers("/consultas/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(acceso -> acceso
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(new RedireccionPorRolHandler())
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(salida -> salida
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(errores -> errores
                .accessDeniedPage("/acceso-denegado")
            );

        return http.build();
    }
}
