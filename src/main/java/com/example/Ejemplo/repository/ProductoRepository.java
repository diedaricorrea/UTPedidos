package com.example.Ejemplo.repository;

import com.example.Ejemplo.models.Producto;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findAllByCategoriaNombre(String nombre);
    Optional<Producto> findProductoById(Integer id);


    @Override
    Page<Producto> findAll(Pageable pageable);

    Page<Producto> findByCategoriaNombre(String nombre, Pageable pageable);
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    Page<Producto> findByCategoriaNombreAndNombreContainingIgnoreCase(String categoria, String nombre, Pageable pageable);
}
