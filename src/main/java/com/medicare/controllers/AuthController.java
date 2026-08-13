package com.medicare.controllers;

import com.medicare.service.CitaMedicaService;
import com.medicare.service.RolService;
import com.medicare.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    private final UsuarioService servicioUsuario;
    private final RolService servicioRol;
    private final CitaMedicaService servicioCita;

    public AuthController(UsuarioService servicioUsuario,
                          RolService servicioRol,
                          CitaMedicaService servicioCita) {
        this.servicioUsuario = servicioUsuario;
        this.servicioRol = servicioRol;
        this.servicioCita = servicioCita;
    }

    @GetMapping("/login")
    public String mostrarFormularioAcceso() {
        return "login";
    }

    @GetMapping({"/", "/inicio"})
    public String mostrarPaginaPrincipal(Model modelo) {
        modelo.addAttribute("tituloVista", "Inicio");
        modelo.addAttribute("totalUsuarios", servicioUsuario.listarTodos().size());
        modelo.addAttribute("totalRoles", servicioRol.listarTodos().size());
        modelo.addAttribute("totalCitas", servicioCita.listarTodas().size());
        modelo.addAttribute("totalCitasActivas", servicioCita.contarActivas());
        return "index";
    }

    @GetMapping("/acceso-denegado")
    public String mostrarAccesoDenegado(Model modelo) {
        modelo.addAttribute("tituloVista", "Acceso denegado");
        return "acceso-denegado";
    }
}
