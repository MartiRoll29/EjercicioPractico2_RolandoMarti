package com.medicare.repository;

import com.medicare.domain.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    @Query("SELECT c FROM CitaMedica c WHERE c.activa = :activa ORDER BY c.fecha ASC")
    List<CitaMedica> buscarPorEstado(@Param("activa") Boolean activa);

    @Query("SELECT c FROM CitaMedica c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY c.fecha ASC")
    List<CitaMedica> buscarPorRangoFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                          @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT c FROM CitaMedica c WHERE LOWER(c.especialidad) LIKE LOWER(CONCAT('%', :especialidad, '%')) ORDER BY c.especialidad ASC")
    List<CitaMedica> buscarPorEspecialidad(@Param("especialidad") String especialidad);

    @Query("SELECT COUNT(c) FROM CitaMedica c WHERE c.activa = true")
    long contarCitasActivas();

    @Query("SELECT c FROM CitaMedica c WHERE c.activa = true ORDER BY c.fecha ASC")
    List<CitaMedica> obtenerCitasActivas();
}
