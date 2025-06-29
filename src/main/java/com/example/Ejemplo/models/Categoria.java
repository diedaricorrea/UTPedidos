package com.example.Ejemplo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer id;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

    @Column(nullable = false, length = 20)
    private String nombre;

    // Constructor vacío
    public Categoria() {
    }

    // Constructor completo
    public Categoria(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}