package com.example.Ejemplo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_pedido_venta")
public class DetallePedido {
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "precio")
    private double precio;
    @Column(name = "subTotal")
    private double subTotal;
}
