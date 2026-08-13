package com.medicare.controllers;

import com.medicare.domain.Rol;
import com.medicare.domain.Usuario;
import com.medicare.service.RolService;
import com.medicare.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    private final UsuarioService servicioUsuario;
    private final RolService servicioRol;

    public UsuarioController(UsuarioService servicioUsuario, RolService servicioRol) {
        this.servicioUsuario = servicioUsuario;
        this.servicioRol = servicioRol;
    }

    @GetMapping
    public String listarUsuarios(Model modelo) {
        modelo.addAttribute("tituloVista", "Gestión de Usuarios");
        modelo.addAttribute("listaUsuarios", servicioUsuario.listarTodos());
        return "usuarios/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model modelo) {
        modelo.addAttribute("tituloVista", "Registrar Usuario");
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("listaRoles", servicioRol.listarTodos());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                   @RequestParam("identificadorRol") Long identificadorRol,
                                   RedirectAttributes atributos) {

        if (usuario.getId() == null && servicioUsuario.existePorEmail(usuario.getEmail())) {
            atributos.addFlashAttribute("mensajeError", "Ya existe un usuario con ese correo.");
            return "redirect:/admin/usuarios/nuevo";
        }

        Optional<Rol> rolSeleccionado = servicioRol.obtenerPorId(identificadorRol);
        rolSeleccionado.ifPresent(usuario::setRol);

        if (usuario.getId() == null) {
            servicioUsuario.guardar(usuario);
            atributos.addFlashAttribute("mensajeExito",
                    "Usuario registrado. Se envió el correo de bienvenida a " + usuario.getEmail() + ".");
        } else {
            servicioUsuario.actualizar(usuario.getId(), usuario);
            atributos.addFlashAttribute("mensajeExito", "Usuario actualizado correctamente.");
        }

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model modelo, RedirectAttributes atributos) {
        Optional<Usuario> encontrado = servicioUsuario.obtenerPorId(id);
        if (encontrado.isEmpty()) {
            atributos.addFlashAttribute("mensajeError", "El usuario solicitado no existe.");
            return "redirect:/admin/usuarios";
        }
        modelo.addAttribute("tituloVista", "Detalle del Usuario");
        modelo.addAttribute("usuario", encontrado.get());
        return "usuarios/detalle";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model modelo, RedirectAttributes atributos) {
        Optional<Usuario> encontrado = servicioUsuario.obtenerPorId(id);
        if (encontrado.isEmpty()) {
            atributos.addFlashAttribute("mensajeError", "El usuario solicitado no existe.");
            return "redirect:/admin/usuarios";
        }
        modelo.addAttribute("tituloVista", "Editar Usuario");
        modelo.addAttribute("usuario", encontrado.get());
        modelo.addAttribute("listaRoles", servicioRol.listarTodos());
        return "usuarios/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes atributos) {
        servicioUsuario.eliminar(id);
        atributos.addFlashAttribute("mensajeExito", "Usuario eliminado correctamente.");
        return "redirect:/admin/usuarios";
    }
}
