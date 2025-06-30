package com.example.Ejemplo.repository;

import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ejemplo.models.Pedido;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findAllByEstado(boolean estado);
}
