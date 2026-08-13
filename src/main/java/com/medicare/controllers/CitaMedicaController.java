package com.medicare.controllers;

import com.medicare.domain.CitaMedica;
import com.medicare.service.CitaMedicaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/citas")
public class CitaMedicaController {

    private final CitaMedicaService servicioCita;

    public CitaMedicaController(CitaMedicaService servicioCita) {
        this.servicioCita = servicioCita;
    }

    @GetMapping
    public String listarCitas(Model modelo) {
        modelo.addAttribute("tituloVista", "Gestión de Citas Médicas");
        modelo.addAttribute("listaCitas", servicioCita.listarTodas());
        modelo.addAttribute("totalCitasActivas", servicioCita.contarActivas());
        return "citas/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model modelo) {
        modelo.addAttribute("tituloVista", "Registrar Cita Médica");
        modelo.addAttribute("cita", new CitaMedica());
        return "citas/formulario";
    }

    @PostMapping("/guardar")
    public String registrarCita(@ModelAttribute("cita") CitaMedica cita, RedirectAttributes atributos) {
        if (cita.getActiva() == null) {
            cita.setActiva(Boolean.FALSE);
        }
        if (cita.getId() == null) {
            servicioCita.guardar(cita);
            atributos.addFlashAttribute("mensajeExito", "Cita médica registrada correctamente.");
        } else {
            servicioCita.actualizar(cita.getId(), cita);
            atributos.addFlashAttribute("mensajeExito", "Cita médica actualizada correctamente.");
        }
        return "redirect:/citas";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model modelo, RedirectAttributes atributos) {
        Optional<CitaMedica> encontrada = servicioCita.obtenerPorId(id);
        if (encontrada.isEmpty()) {
            atributos.addFlashAttribute("mensajeError", "La cita médica solicitada no existe.");
            return "redirect:/citas";
        }
        modelo.addAttribute("tituloVista", "Detalle de la Cita Médica");
        modelo.addAttribute("cita", encontrada.get());
        return "citas/detalle";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model modelo, RedirectAttributes atributos) {
        Optional<CitaMedica> encontrada = servicioCita.obtenerPorId(id);
        if (encontrada.isEmpty()) {
            atributos.addFlashAttribute("mensajeError", "La cita médica solicitada no existe.");
            return "redirect:/citas";
        }
        modelo.addAttribute("tituloVista", "Editar Cita Médica");
        modelo.addAttribute("cita", encontrada.get());
        return "citas/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable Long id, RedirectAttributes atributos) {
        servicioCita.eliminar(id);
        atributos.addFlashAttribute("mensajeExito", "Cita médica eliminada correctamente.");
        return "redirect:/citas";
    }
}
