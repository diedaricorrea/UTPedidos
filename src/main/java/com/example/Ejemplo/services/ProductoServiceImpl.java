package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Carrito;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.repository.CarritoRepository;
import com.example.Ejemplo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final CarritoRepository carritoRepository;

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productosRepository, CarritoRepository carritoRepository) {
        this.productoRepository = productosRepository;
        this.carritoRepository = carritoRepository;
    }

    @Override
    public Optional<Producto> findProductoById(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public Optional<Producto> findProductoPorNombre(String nombre) {
        // Método no implementado
        return Optional.empty();
    }

    @Override
    public Producto saveUser(Producto producto) {
        // Método no implementado
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        // Método no implementado
    }

    public List<Producto> findAllByCategoriaNombre(String nombre) {
        return productoRepository.findProductoByCategoria_Nombre(nombre);
    }

    @Override
    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> findAllProductosById(int id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(List::of).orElse(List.of());
    }


    @Override
    public List<Carrito> obtenerCarritosPorUsuario(int id) {
        return carritoRepository.findByIdUsuario_IdUsuario(id);
    }


    @Override
    public void saveCarrito(int id, int idProducto, int cantidad, double total) {
        carritoRepository.saveCarritoByIdUsuario(id, idProducto,cantidad,total);
    }

    @Override
    public int actualizarProductoAgregado(int idUsuario, int idProducto, int cantidad, double subTotal){
        return carritoRepository.updateCantidadCarrito(idUsuario,idProducto,cantidad,subTotal);
    }

    @Override
    public int eliminarProductoAgregado(int idUsuario, int idProducto){
        return carritoRepository.deleteCarritoByUsuarioIdAndProductoId(idUsuario,idProducto);
    }

    @Override
    public Producto saveProduct(Producto producto) {
        if (producto.getImagenUrl().isEmpty()) {
            producto.setImagenUrl("/imagenes/imagenpordefecto.png");
        }
        return productoRepository.save(producto);
    }

    @Override
    public Page<Producto> findAllProductosPaginado(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public Page<Producto> obtenerProductosPorCategoriaPaginado(String categoria, Pageable pageable) {
        return productoRepository.findByCategoriaNombre(categoria, pageable);
    }

    @Override
    public void deleteProductById(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Page<Producto> buscarPorCategoriaYNombre(String categoria, String nombre, Pageable pageable) {
        if ((categoria == null || categoria.isEmpty()) && (nombre == null || nombre.isEmpty())) {
            return productoRepository.findAll(pageable);
        } else if (categoria != null && !categoria.isEmpty() && (nombre == null || nombre.isEmpty())) {
            return productoRepository.findByCategoriaNombre(categoria, pageable);
        } else if ((categoria == null || categoria.isEmpty()) && nombre != null && !nombre.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            return productoRepository.findByCategoriaNombreAndNombreContainingIgnoreCase(categoria, nombre, pageable);
        }
    }
}
