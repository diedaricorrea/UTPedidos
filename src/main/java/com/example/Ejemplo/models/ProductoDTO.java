package com.example.Ejemplo.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductoDTO {
    private int id;
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private Boolean estado;
    private String imagenUrl;

    public ProductoDTO(int id, int idCategoria, String nombre, String descripcion, Integer stock, Boolean estado, String imagenUrl) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
    }

    public ProductoDTO(int idCategoria, String nombre, String descripcion, Integer stock, Boolean estado, String imagenUrl) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
