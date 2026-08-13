package com.medicare.repository;

import com.medicare.domain.Usuario;
import com.medicare.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol ORDER BY u.nombre ASC")
    List<Usuario> buscarPorRol(@Param("rol") Rol rol);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol")
    long contarPorRol(@Param("rol") Rol rol);

    @Query("SELECT u FROM Usuario u WHERE u.activo = true ORDER BY u.fechaCreacion DESC")
    List<Usuario> obtenerUsuariosActivos();
}
