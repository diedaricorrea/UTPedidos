package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosServiceImpl implements ProductosService {
    private final ProductosRepository productosRepository;

    @Autowired
    public ProductosServiceImpl(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    @Override
    public List<Producto> findAllProductos() {
        return productosRepository.findAll();
    }

    @Override
    public Optional<Producto> findProductoById(Integer id) {
        return productosRepository.findById(id);
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
        return productosRepository.findAllByCategoriaNombre(nombre);
    }
}
