package com.example.Ejemplo.controllers;

import com.example.Ejemplo.services.PedidosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    public PedidosServiceImpl pedidosServiceImpl;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pedidos", pedidosServiceImpl.obtenerDetallePedidos());
        return "administrador/pendientes";
    }

}
