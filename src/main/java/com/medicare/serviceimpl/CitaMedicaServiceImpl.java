package com.medicare.serviceimpl;

import com.medicare.domain.CitaMedica;
import com.medicare.repository.CitaMedicaRepository;
import com.medicare.service.CitaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CitaMedicaServiceImpl implements CitaMedicaService {

    @Autowired
    private CitaMedicaRepository citaMedicaRepository;

    @Override
    public List<CitaMedica> listarTodas() {
        return citaMedicaRepository.findAll();
    }

    @Override
    public Optional<CitaMedica> obtenerPorId(Long id) {
        return citaMedicaRepository.findById(id);
    }

    @Override
    public CitaMedica guardar(CitaMedica citaMedica) {
        return citaMedicaRepository.save(citaMedica);
    }

    @Override
    public void actualizar(Long id, CitaMedica citaMedicaActualizada) {
        Optional<CitaMedica> citaOpcional = citaMedicaRepository.findById(id);
        if (citaOpcional.isPresent()) {
            CitaMedica cita = citaOpcional.get();
            cita.setPacienteNombre(citaMedicaActualizada.getPacienteNombre());
            cita.setEspecialidad(citaMedicaActualizada.getEspecialidad());
            cita.setFecha(citaMedicaActualizada.getFecha());
            cita.setCosto(citaMedicaActualizada.getCosto());
            cita.setActiva(citaMedicaActualizada.getActiva());
            citaMedicaRepository.save(cita);
        }
    }

    @Override
    public void eliminar(Long id) {
        citaMedicaRepository.deleteById(id);
    }

    @Override
    public List<CitaMedica> obtenerPorEstado(Boolean activa) {
        return citaMedicaRepository.buscarPorEstado(activa);
    }

    @Override
    public List<CitaMedica> obtenerEnRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return citaMedicaRepository.buscarPorRangoFechas(fechaInicio, fechaFin);
    }

    @Override
    public List<CitaMedica> buscarPorEspecialidad(String especialidad) {
        return citaMedicaRepository.buscarPorEspecialidad(especialidad);
    }

    @Override
    public long contarActivas() {
        return citaMedicaRepository.contarCitasActivas();
    }

    @Override
    public List<CitaMedica> obtenerActivas() {
        return citaMedicaRepository.obtenerCitasActivas();
    }
}
