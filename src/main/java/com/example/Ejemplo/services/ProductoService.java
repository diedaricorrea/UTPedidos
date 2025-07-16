package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Carrito;
import com.example.Ejemplo.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> findAllProductos();
    Optional<Producto> findProductoById(Integer id);
    Optional<Producto> findProductoPorNombre(String nombre);

    List<Producto> findAllProductosById(int id);

    List<Carrito> obtenerCarritosPorUsuario(int id);

    void saveCarrito(int id, int idProducto, int cantidad, double total);

    int actualizarProductoAgregado(int idUsuario, int idProducto, int cantidad, double subTotal);

    int eliminarProductoAgregado(int idUsuario, int idProducto);

    Producto saveProduct(Producto producto);

    Page<Producto> findAllProductosPaginado(Pageable pageable);

    Page<Producto> obtenerProductosPorCategoriaPaginado(String categoria, Pageable pageable);

    void deleteProductById(Integer id);

    Page<Producto> buscarPorCategoriaYNombre(String categoria, String nombre, Pageable pageable);
    List<Producto> findAll();
    List<Producto> findRecent();
    Producto findById(Integer id);


    Producto save(Producto producto, MultipartFile imagen);

    void delete(Integer id);
}
