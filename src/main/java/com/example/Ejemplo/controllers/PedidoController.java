package com.example.Ejemplo.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.Ejemplo.models.Pedido;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidoController {
    @GetMapping("/pedidos")
    public String index(Model model) {
        model.addAttribute("pedidos", getPedidos());
        return "pendientes";
    }

    public List<Pedido> getPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        /*pedidos.add(new Pedidos(1, "Pizza", "Juan", "Pizza de pepperoni", 10.0, "10:00"));
        pedidos.add(new Pedidos(2, "Hamburguesa", "Pedro", "Hamburguesa con queso", 5.0, "11:00"));
        pedidos.add(new Pedidos(3, "Arroz con pollo", "Diego", "Menu economico", 8.0, "11:00"));
        */
        return pedidos;
    }
}
