package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Pedido;
import com.example.Ejemplo.models.PedidoResumenDTO;
import com.example.Ejemplo.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<PedidoResumenDTO> obtenerDetallePedidos() {
        List<Pedido> pedidos = pedidosRepository.findAll();

        return pedidos.stream().map(pedido -> {
            String nombreUsuario = pedido.getUsuario().getNombre();
            LocalDateTime fechaEntrega = pedido.getFechaEntrega();
            boolean estado = pedido.isEstado();
            List<String> productos = pedido.getDetallePedido().stream()
                    .map(d -> d.getProducto().getNombre())
                    .collect(Collectors.toList());

            return new PedidoResumenDTO(nombreUsuario, productos, fechaEntrega,estado);
        }).collect(Collectors.toList());
    }
}
