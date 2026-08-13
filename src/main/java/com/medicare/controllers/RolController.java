package com.medicare.controllers;

import com.medicare.domain.Rol;
import com.medicare.service.RolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/roles")
public class RolController {

    private final RolService servicioRol;

    public RolController(RolService servicioRol) {
        this.servicioRol = servicioRol;
    }

    @GetMapping
    public String listarRoles(Model modelo) {
        modelo.addAttribute("tituloVista", "Gestión de Roles");
        modelo.addAttribute("listaRoles", servicioRol.listarTodos());
        return "roles/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model modelo) {
        modelo.addAttribute("tituloVista", "Registrar Rol");
        modelo.addAttribute("rol", new Rol());
        return "roles/formulario";
    }

    @PostMapping("/guardar")
    public String registrarRol(@ModelAttribute("rol") Rol rol, RedirectAttributes atributos) {
        if (rol.getId() == null) {
            if (servicioRol.existePorNombre(rol.getNombre())) {
                atributos.addFlashAttribute("mensajeError", "Ya existe un rol con ese nombre.");
                return "redirect:/admin/roles/nuevo";
            }
            servicioRol.guardar(rol);
            atributos.addFlashAttribute("mensajeExito", "Rol registrado correctamente.");
        } else {
            servicioRol.actualizar(rol.getId(), rol);
            atributos.addFlashAttribute("mensajeExito", "Rol actualizado correctamente.");
        }
        return "redirect:/admin/roles";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model modelo, RedirectAttributes atributos) {
        Optional<Rol> encontrado = servicioRol.obtenerPorId(id);
        if (encontrado.isEmpty()) {
            atributos.addFlashAttribute("mensajeError", "El rol solicitado no existe.");
            return "redirect:/admin/roles";
        }
        modelo.addAttribute("tituloVista", "Editar Rol");
        modelo.addAttribute("rol", encontrado.get());
        return "roles/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            servicioRol.eliminar(id);
            atributos.addFlashAttribute("mensajeExito", "Rol eliminado correctamente.");
        } catch (Exception excepcion) {
            atributos.addFlashAttribute("mensajeError",
                    "No se puede eliminar el rol porque tiene usuarios asociados.");
        }
        return "redirect:/admin/roles";
    }
}
