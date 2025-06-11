package com.example.Ejemplo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(name = "nombre")
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max = 50, message = "El nombre debe tener minimo 3 caracteres")
    private String nombre;

    @Column(name = "correo",unique = true,nullable = false)
    private String correo;

    @Column(name = "password",nullable = false)
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

    public Usuario() {}

    public Usuario(LocalDateTime fechaIngreso, Rol rol, boolean estado, String password, String correo, String nombre) {
        this.fechaIngreso = fechaIngreso;
        this.rol = rol;
        this.estado = estado;
        this.password = password;
        this.correo = correo;
        this.nombre = nombre;
    }
    public Usuario(String nombre, String correo, String password ,Rol rol) {
        this.rol = rol;
        this.password = password;
        this.correo = correo;
        this.nombre = nombre;
    }

    public Usuario(int idUsuario, LocalDateTime fechaIngreso, Rol rol, boolean estado, String password, String correo, String nombre) {
        this.idUsuario = idUsuario;
        this.fechaIngreso = fechaIngreso;
        this.rol = rol;
        this.estado = estado;
        this.password = password;
        this.correo = correo;
        this.nombre = nombre;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
