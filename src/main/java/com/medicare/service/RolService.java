package com.medicare.service;

import com.medicare.domain.Rol;
import java.util.List;
import java.util.Optional;

public interface RolService {

    List<Rol> listarTodos();

    Optional<Rol> obtenerPorId(Long id);

    Optional<Rol> obtenerPorNombre(String nombre);

    Rol guardar(Rol rol);

    void actualizar(Long id, Rol rol);

    void eliminar(Long id);

    boolean existePorNombre(String nombre);
}
