package com.medicare.serviceimpl;

import com.medicare.domain.Rol;
import com.medicare.repository.RolRepository;
import com.medicare.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> obtenerPorId(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Optional<Rol> obtenerPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    @Override
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void actualizar(Long id, Rol rolActualizado) {
        Optional<Rol> rolOpcional = rolRepository.findById(id);
        if (rolOpcional.isPresent()) {
            Rol rol = rolOpcional.get();
            rol.setNombre(rolActualizado.getNombre());
            rolRepository.save(rol);
        }
    }

    @Override
    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return rolRepository.findByNombre(nombre).isPresent();
    }
}
