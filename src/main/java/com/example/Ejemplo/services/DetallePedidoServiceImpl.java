package com.example.Ejemplo.services;

import com.example.Ejemplo.models.DetallePedido;
import com.example.Ejemplo.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Override
    public List<DetallePedido> findAllDetallePedido() {
        return detallePedidoRepository.findAll();
    }
}
