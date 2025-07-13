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
    private long id_venta;
    private long id_detalle_venta;
    private long id_user;
    private String fecha;
    private String estado;
}