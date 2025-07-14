package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.Notificacion;
import com.example.Ejemplo.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private NotificacionService servicioNotificacion;

    @ModelAttribute("notificaciones")
    public List<Notificacion> notificacionesActuales() {
        return servicioNotificacion.findAllByUsuario_IdUsuario(1);
    }
}