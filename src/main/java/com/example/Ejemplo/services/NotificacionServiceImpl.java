package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Notificacion;
import com.example.Ejemplo.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Autowired
    public NotificacionServiceImpl(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void sendNotificacion(int idUsuario, String mensaje) {
        notificacionRepository.sendNotificacion(idUsuario, mensaje);
    }

    @Override
    public List<Notificacion> findAllByUsuario_IdUsuario(int idUsuario) {
        return notificacionRepository.findAllByUsuario_IdUsuario(idUsuario); 
    }
}
