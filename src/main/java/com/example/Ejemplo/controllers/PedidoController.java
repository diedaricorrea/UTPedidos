package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.*;
import com.example.Ejemplo.security.UsuarioDetails;
import com.example.Ejemplo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final ProductoServiceImpl productoServiceImpl;

    @Autowired
    public PedidoController(PedidosServiceImpl pedidoServiceImpl, UsuarioServiceImpl usuarioServiceImpl, CarritoServiceImpl carritoServiceImpl, DetallePedidoServiceImpl detallePedidoServiceImpl, ProductoServiceImpl productoServiceImpl) {
        this.pedidoServiceImpl = pedidoServiceImpl;
        this.usuarioServiceImpl = usuarioServiceImpl;
        this.carritoServiceImpl = carritoServiceImpl;
        this.detallePedidoServiceImpl = detallePedidoServiceImpl;
        this.productoServiceImpl = productoServiceImpl;
    }

    @GetMapping("/pedido")
    public String verPagos(@AuthenticationPrincipal UsuarioDetails userDetails, Model model) {
        Usuario usuario = userDetails.getUsuario();
        int idUsuario = usuario.getIdUsuario();
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
    public String pedir(@AuthenticationPrincipal UsuarioDetails userDetails, @RequestParam("horaEntrega") LocalTime horaEntrega, Model model) {
        Pedido pedido = new Pedido();
        Usuario usuario = userDetails.getUsuario();
        int idUsuario = usuario.getIdUsuario();
        pedido.setUsuario(usuarioServiceImpl.findUsuarioById(idUsuario).orElse(null));
        pedido.setFechaEntrega(horaEntrega);
        pedido.setCodigoPedido(pedidoServiceImpl.generarCodigoUnico());
        Pedido pedidoGuardado = pedidoServiceImpl.guardarPedido(pedido);
        for (Producto pro: obtenerTodosProductos(idUsuario)){
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedidoGuardado);
            detallePedido.setProducto(pro);

            DetallePedidoId detalleId = new DetallePedidoId(
                    pedidoGuardado.getIdPedido(),
                    pro.getIdProducto()
            );
            detallePedido.setDetallepedidoId(detalleId);
            //disminuir stock de los productos adquiridos
            productoServiceImpl.disminuirStock();
            detallePedidoServiceImpl.saveDetallePedido(detallePedido);
        }
        carritoServiceImpl.limpiarCarrito(idUsuario);
        return "redirect:/pedidos/pedido/";
    }


    List<Producto> obtenerTodosProductos(int idUsuario){
        return carritoServiceImpl.obtenerCarritosPorUsuario(idUsuario).stream().map(carrito -> carrito.getIdProducto()).collect(Collectors.toList());
    }

}
