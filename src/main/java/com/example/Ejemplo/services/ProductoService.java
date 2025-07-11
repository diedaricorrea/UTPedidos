package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Carrito;
import com.example.Ejemplo.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> findAllProductos();
    Optional<Producto> findProductoById(Integer id);
    Optional<Producto> findProductoPorNombre(String nombre);
    Producto saveUser(Producto producto);
    void deleteUserById(Long id);

    List<Producto> findAllProductosById(int id);

    List<Carrito> obtenerCarritosPorUsuario(int id);

    void saveCarrito(int id, int idProducto, int cantidad, double total);

    int actualizarProductoAgregado(int idUsuario, int idProducto, int cantidad, double subTotal);

    int eliminarProductoAgregado(int idUsuario, int idProducto);
}
