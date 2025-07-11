package com.example.Ejemplo.services;

import com.example.Ejemplo.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
