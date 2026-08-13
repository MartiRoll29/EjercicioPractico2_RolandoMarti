package com.medicare.controllers;

import com.medicare.domain.Rol;
import com.medicare.service.CitaMedicaService;
import com.medicare.service.RolService;
import com.medicare.service.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/consultas")
public class ConsultasController {

    private final CitaMedicaService servicioCita;
    private final UsuarioService servicioUsuario;
    private final RolService servicioRol;

    public ConsultasController(CitaMedicaService servicioCita,
                               UsuarioService servicioUsuario,
                               RolService servicioRol) {
        this.servicioCita = servicioCita;
        this.servicioUsuario = servicioUsuario;
        this.servicioRol = servicioRol;
    }

    private void cargarDatosBase(Model modelo) {
        modelo.addAttribute("tituloVista", "Consultas Avanzadas");
        modelo.addAttribute("listaRoles", servicioRol.listarTodos());
        modelo.addAttribute("totalCitasActivas", servicioCita.contarActivas());
    }

    @GetMapping
    public String mostrarPanelConsultas(Model modelo) {
        cargarDatosBase(modelo);
        return "consultas";
    }

    @PostMapping("/estado")
    public String consultarPorEstado(@RequestParam("estadoCita") Boolean estadoCita, Model modelo) {
        cargarDatosBase(modelo);
        modelo.addAttribute("resultadoCitas", servicioCita.obtenerPorEstado(estadoCita));
        modelo.addAttribute("descripcionConsulta",
                "Citas médicas con estado: " + (Boolean.TRUE.equals(estadoCita) ? "Activas" : "Inactivas"));
        return "consultas";
    }

    @PostMapping("/rango-fechas")
    public String consultarPorRangoFechas(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model modelo) {
        cargarDatosBase(modelo);
        modelo.addAttribute("resultadoCitas", servicioCita.obtenerEnRangoFechas(desde, hasta));
        modelo.addAttribute("descripcionConsulta",
                "Citas médicas entre " + desde + " y " + hasta);
        return "consultas";
    }

    @PostMapping("/especialidad")
    public String consultarPorEspecialidad(@RequestParam("textoEspecialidad") String textoEspecialidad,
                                           Model modelo) {
        cargarDatosBase(modelo);
        modelo.addAttribute("resultadoCitas", servicioCita.buscarPorEspecialidad(textoEspecialidad));
        modelo.addAttribute("descripcionConsulta",
                "Citas médicas cuya especialidad contiene: \"" + textoEspecialidad + "\"");
        return "consultas";
    }

    @PostMapping("/usuarios-rol")
    public String consultarUsuariosPorRol(@RequestParam("identificadorRol") Long identificadorRol, Model modelo) {
        cargarDatosBase(modelo);
        Optional<Rol> rolSeleccionado = servicioRol.obtenerPorId(identificadorRol);
        if (rolSeleccionado.isPresent()) {
            Rol rol = rolSeleccionado.get();
            modelo.addAttribute("resultadoUsuarios", servicioUsuario.obtenerPorRol(rol));
            modelo.addAttribute("totalUsuariosRol", servicioUsuario.contarPorRol(rol));
            modelo.addAttribute("descripcionConsulta", "Usuarios con el rol: " + rol.getNombre());
        }
        return "consultas";
    }
}
