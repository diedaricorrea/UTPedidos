package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Rol;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> findUsuarioPorNombre(String nombre) {
        // Método no implementado
        return Optional.empty();
    }

    @Override
    public Usuario saveUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void updateUsuario(Integer id) {
        // Método no implementado
    }

    @Override
    public List<Usuario> findAllUsuariosByNotRol(Rol rol) {
        return usuarioRepository.findByRolNot(rol);
    }



    @Override
    public Optional<Usuario> findUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

}
