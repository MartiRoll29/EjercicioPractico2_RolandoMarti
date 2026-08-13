package com.medicare.service;

import com.medicare.domain.CitaMedica;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CitaMedicaService {

    List<CitaMedica> listarTodas();

    Optional<CitaMedica> obtenerPorId(Long id);

    CitaMedica guardar(CitaMedica citaMedica);

    void actualizar(Long id, CitaMedica citaMedica);

    void eliminar(Long id);

    List<CitaMedica> obtenerPorEstado(Boolean activa);

    List<CitaMedica> obtenerEnRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    List<CitaMedica> buscarPorEspecialidad(String especialidad);

    long contarActivas();

    List<CitaMedica> obtenerActivas();
}
