package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductosService {
    List<Producto> findAllProductos();
    Optional<Producto> findProductoById(Integer id);
    Optional<Producto> findProductoPorNombre(String nombre);
    Producto saveUser(Producto producto);
    void deleteUserById(Long id);
}
