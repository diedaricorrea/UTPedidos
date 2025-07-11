package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Carrito;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.repository.CarritoRepository;
import com.example.Ejemplo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return Optional.empty();
    }

    @Override
    public Producto saveUser(Producto producto) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    public List<Producto> findAllByCategoriaNombre(String nombre) {
        return productoRepository.findAllByCategoriaNombre(nombre);
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
        return carritoRepository.findByUsuarioId(id);
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
        return carritoRepository.deleteByUsuarioIdAndProductoId(idUsuario,idProducto);
    }
}
