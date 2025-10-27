package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Categoria;
import com.example.Ejemplo.repository.CategoriaRepository;
import com.example.Ejemplo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> findById(Integer id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Optional<Categoria> findByNombre(String nombre) {
        Categoria categoria = categoriaRepository.findByNombre(nombre);
        return Optional.ofNullable(categoria);
    }

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {
        // Validar que el nombre no esté vacío
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
        
        // Validar que no exista otra categoría con el mismo nombre (excepto si es la misma)
        Categoria existente = categoriaRepository.findByNombre(categoria.getNombre());
        if (existente != null && !existente.getIdCategoria().equals(categoria.getIdCategoria())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }
        
        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // Verificar si la categoría tiene productos asociados
        long cantidadProductos = countProductosByCategoria(id);
        if (cantidadProductos > 0) {
            throw new IllegalStateException("No se puede eliminar la categoría porque tiene " + cantidadProductos + " productos asociados");
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre) != null;
    }

    @Override
    public long countProductosByCategoria(Integer idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        if (categoria.isPresent() && categoria.get().getProductos() != null) {
            return categoria.get().getProductos().size();
        }
        return 0;
    }
}
