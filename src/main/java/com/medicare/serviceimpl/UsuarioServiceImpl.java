package com.medicare.serviceimpl;

import com.medicare.domain.Usuario;
import com.medicare.domain.Rol;
import com.medicare.repository.UsuarioRepository;
import com.medicare.service.UsuarioService;
import com.medicare.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        enviarCorreoBienvenida(usuarioGuardado);
        return usuarioGuardado;
    }

    @Override
    public void actualizar(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOpcional = usuarioRepository.findById(id);
        if (usuarioOpcional.isPresent()) {
            Usuario usuario = usuarioOpcional.get();
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setActivo(usuarioActualizado.getActivo());
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<Usuario> obtenerPorRol(Rol rol) {
        return usuarioRepository.buscarPorRol(rol);
    }

    @Override
    public long contarPorRol(Rol rol) {
        return usuarioRepository.contarPorRol(rol);
    }

    @Override
    public List<Usuario> obtenerActivos() {
        return usuarioRepository.obtenerUsuariosActivos();
    }

    @Override
    public void enviarCorreoBienvenida(Usuario usuario) {
        emailService.enviarBienvenida(usuario);
    }
}
