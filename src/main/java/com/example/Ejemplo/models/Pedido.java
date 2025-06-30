package com.example.Ejemplo.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;
    @Column(name = "estado")
    private boolean estado;

    @PrePersist
    protected void onCreate() {
        fechaPedido = LocalDateTime.now();
    }
}
