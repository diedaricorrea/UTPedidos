package com.example.Ejemplo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_pedido")
public class DetallePedido {
    @EmbeddedId
    private DetallePedidoId detallepedidoId;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto Producto;
}
