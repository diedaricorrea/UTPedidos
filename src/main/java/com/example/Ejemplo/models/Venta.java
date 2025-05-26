package com.example.Ejemplo.models;

public class Venta {
    private long id_venta;
    private long id_detalle_venta;
    private long id_user;
    private String fecha;
    private String estado;

    public Venta(long id_venta, long id_detalle_venta, long id_user, String fecha, String estado) {
        this.id_venta = id_venta;
        this.id_detalle_venta = id_detalle_venta;
        this.id_user = id_user;
        this.fecha = fecha;
        this.estado = estado;
    }

    public long getId_venta() {
        return id_venta;
    }

    public void setId_venta(long id_venta) {
        this.id_venta = id_venta;
    }

    public long getId_detalle_venta() {
        return id_detalle_venta;
    }

    public void setId_detalle_venta(long id_detalle_venta) {
        this.id_detalle_venta = id_detalle_venta;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}