package com.example.Ejemplo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(name = "nombre")
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max = 50, message = "El nombre debe tener minimo 3 caracteres")
    private String nombre;

    @Column(name = "correo", unique = true, nullable = false)
    private String correo;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Debes rellenar este campo")
    @Size(min = 8, max = 50, message = "La contraseña debe ser de minimo 8 digitos")
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name = "fecha_ingreso", updatable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "estado")
    private boolean estado;

    @PrePersist
    protected void onCreate() {
        fechaIngreso = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;
}
