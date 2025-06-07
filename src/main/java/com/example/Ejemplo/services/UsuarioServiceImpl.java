package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Usuario;

import java.util.List;
import java.util.Optional;

public class UsuarioServiceImpl implements UsuarioService {
    @Override
    public List<Usuario> findAllUsuarios() {
        return List.of();
    }

    @Override
    public Optional<Usuario> findUsuarioById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findUsuarioPorNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public Usuario saveUser(Usuario usuario) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }
}
