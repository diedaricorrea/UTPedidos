package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Pedido;
import com.example.Ejemplo.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosServiceImpl implements PedidosService {

    private PedidosRepository pedidosRepository;

    @Autowired
    public PedidosServiceImpl(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }


    @Override
    public List<Pedido> findAllPedidos() {
        return pedidosRepository.findAllByEstado(false);
    }
}
