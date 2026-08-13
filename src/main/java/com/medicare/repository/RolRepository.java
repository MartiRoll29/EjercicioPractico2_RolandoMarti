package com.medicare.repository;

import com.medicare.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);

    @Query("SELECT r FROM Rol r WHERE r.nombre = ?1")
    Optional<Rol> buscarPorNombrePersonalizado(String nombre);
}
