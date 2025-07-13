package com.example.Ejemplo.repository;

import com.example.Ejemplo.models.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findProductoByCategoria_Nombre(String categoriaNombre);

    @Override
    Page<Producto> findAll(Pageable pageable);

    Page<Producto> findByCategoriaNombre(String nombre, Pageable pageable);
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    Page<Producto> findByCategoriaNombreAndNombreContainingIgnoreCase(String categoria, String nombre, Pageable pageable);
}
