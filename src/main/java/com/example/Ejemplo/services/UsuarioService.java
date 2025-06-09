package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAllUsuarios();
    Optional<Usuario> findUsuarioById(Integer id);
    Optional<Usuario> findUsuarioPorNombre(String nombre);
    Usuario saveUser(Usuario usuario);
    void deleteUserById(Integer id,boolean deleted);
}
