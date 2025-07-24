package com.example.Ejemplo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_venta;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "detalleventa_id_venta", referencedColumnName = "id_venta"),
            @JoinColumn(name = "detalleventa_id_producto", referencedColumnName = "id_producto")
    })
    private DetalleVenta detalleventa;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private String tipoPago;

    private double total;

    private String fecha;
}