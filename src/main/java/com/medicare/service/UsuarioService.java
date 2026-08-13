package com.medicare.service;

import com.medicare.domain.Usuario;
import com.medicare.domain.Rol;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listarTodos();

    Optional<Usuario> obtenerPorId(Long id);

    Optional<Usuario> obtenerPorEmail(String email);

    Usuario guardar(Usuario usuario);

    void actualizar(Long id, Usuario usuario);

    void eliminar(Long id);

    boolean existePorEmail(String email);

    List<Usuario> obtenerPorRol(Rol rol);

    long contarPorRol(Rol rol);

    List<Usuario> obtenerActivos();

    void enviarCorreoBienvenida(Usuario usuario);
}
