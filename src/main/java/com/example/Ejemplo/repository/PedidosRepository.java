package com.example.Ejemplo.repository;

import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ejemplo.models.Pedido;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findAllByEstado(boolean estado);

    @Modifying
    @Transactional
    @Query(value = "UPDATE pedidos SET estado = 1 WHERE id_usuario = :idUsuario ",nativeQuery = true)
    int deleteByUsuarioId(@Param("idUsuario") int idUsuario);
}
