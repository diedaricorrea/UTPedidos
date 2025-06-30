package com.example.Ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ejemplo.models.Pedido;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<Pedido,Integer> {
    public List<Pedido> findPedidosNotEstatus(int estado);
}
