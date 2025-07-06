package com.example.Ejemplo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResumenDTO {
    private String nombreUsuario;
    private List<String> productos;
    private LocalDateTime fechaEntrega;
    private boolean estado;
}
