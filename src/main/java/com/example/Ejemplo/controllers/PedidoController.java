package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.DetallePedido;
import com.example.Ejemplo.models.DetallePedidoId;
import com.example.Ejemplo.models.Pedido;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.services.CarritoServiceImpl;
import com.example.Ejemplo.services.DetallePedidoServiceImpl;
import com.example.Ejemplo.services.PedidosServiceImpl;
import com.example.Ejemplo.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidosServiceImpl pedidoServiceImpl;
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final CarritoServiceImpl carritoServiceImpl;
    private final DetallePedidoServiceImpl detallePedidoServiceImpl;

    @Autowired
    public PedidoController(PedidosServiceImpl pedidoServiceImpl, UsuarioServiceImpl usuarioServiceImpl, CarritoServiceImpl carritoServiceImpl, DetallePedidoServiceImpl detallePedidoServiceImpl) {
        this.pedidoServiceImpl = pedidoServiceImpl;
        this.usuarioServiceImpl = usuarioServiceImpl;
        this.carritoServiceImpl = carritoServiceImpl;
        this.detallePedidoServiceImpl = detallePedidoServiceImpl;
    }

    @GetMapping("/pedido/{idUsuario}")
    public String verPagos(@PathVariable Integer idUsuario, Model model) {
        model.addAttribute("pedidos", pedidoServiceImpl.findByUsuario_Id(idUsuario));
        model.addAttribute("pedidosDetalle", pedidoServiceImpl.obtenerDetallePedidosPorId(idUsuario));
        return "usuario/pedido";
    }

    @GetMapping("/admin")
    public String verPedidosPendientes(Model model) {
        model.addAttribute("pedidosPendientes", pedidoServiceImpl.obtenerDetallePedidos());
        return "administrador/pendientes";
    }

    @PostMapping("/pedir")
    public String pedir(@RequestParam("idUsuario") int idUsuario, @RequestParam("horaEntrega") LocalTime horaEntrega, Model model) {
        Pedido pedido = new Pedido();
        DetallePedido detallePedido = new DetallePedido();
        pedido.setUsuario(usuarioServiceImpl.findUsuarioById(idUsuario).orElse(null));
        pedido.setFechaEntrega(horaEntrega);
        pedido.setCodigoPedido(pedidoServiceImpl.generarCodigoUnico());
        Pedido pedidoGuardado = pedidoServiceImpl.guardarPedido(pedido);
        for (Producto pro: obtenerTodosProductos(idUsuario)){
            detallePedido.setPedido(pedidoGuardado);
            detallePedido.setProducto(pro);

            DetallePedidoId detalleId = new DetallePedidoId(
                    pedidoGuardado.getIdPedido(),
                    pro.getIdProducto()
            );
            detallePedido.setDetallepedidoId(detalleId);
            detallePedidoServiceImpl.saveDetallePedido(detallePedido);
            System.out.println(pedidoGuardado.getIdPedido());
        }
        carritoServiceImpl.limpiarCarrito(idUsuario);
        return "redirect:/pedidos/pedido/1";
    }


    List<Producto> obtenerTodosProductos(int idUsuario){
        return carritoServiceImpl.obtenerCarritosPorUsuario(idUsuario).stream().map(carrito -> carrito.getIdProducto()).collect(Collectors.toList());
    }

}
