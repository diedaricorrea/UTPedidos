package com.example.Ejemplo.repository;

import com.example.Ejemplo.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<Producto, Integer> {
}
