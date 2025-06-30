package com.example.Ejemplo.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.Ejemplo.models.DetallePedido;
import com.example.Ejemplo.models.Pedido;

import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.models.ProductoDTO;
import com.example.Ejemplo.services.DetallePedidoServiceImpl;
import com.example.Ejemplo.services.PedidosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidoController {
    @Autowired
    public PedidosServiceImpl pedidosServiceImpl;
    @Autowired
    public DetallePedidoServiceImpl detallePedidoServiceImpl;

    @GetMapping("/pedidos")
    public String index(Model model) {
        model.addAttribute("pedidos", pedidosServiceImpl.findAllPedidos());
        model.addAttribute("productosPedido", getProducts());
        return "pendientes";
    }

    List<ProductoDTO> getProducts(){
        List<ProductoDTO> productos = new ArrayList<>();
        productos.add(new ProductoDTO(1,2,"Arroz con pollo","Arroz con pollo mas jugo",200,true,"/imagenes/pollo.img"));
        // Segundo producto (ID = 2)
        productos.add(new ProductoDTO(2, 3, "Ceviche", "Ceviche de pescado con canchita", 180, true, "/imagenes/ceviche.img"));

// Tercer producto (ID = 3)
        productos.add(new ProductoDTO(3, 1, "Lomo saltado", "Lomo saltado con arroz y papas fritas", 220, true, "/imagenes/lomo.img"));

// Cuarto producto (ID = 4)
        productos.add(new ProductoDTO(4, 2, "Aji de gallina", "Aji de gallina con arroz y papa", 190, true, "/imagenes/aji.img"));
        // Producto 5 (ID: 5)
        productos.add(new ProductoDTO(5, 1, "Causa Limeña", "Pasta de papa amarilla con relleno de pollo o atún", 200, true, "/imagenes/causa.jpg"));
/*
// Producto 6 (ID: 6)
        productos.add(new ProductoDTO(6, 3, "Pollo a la Brasa", "Pollo entero asado con especias acompañado de papas", 100, true, "/imagenes/pollo_brasa.jpg"));

// Producto 7 (ID: 7)
        productos.add(new ProductoDTO(7, 2, "Tallarines Verdes", "Tallarines con salsa de albahaca y carne molida", 300, true, "/imagenes/tallarines.jpg"));

// Producto 8 (ID: 8)
        productos.add(new ProductoDTO(8, 1, "Anticuchos", "Brochetas de corazón de res con maíz y papa", 170, true, "/imagenes/anticuchos.jpg"));

// Producto 9 (ID: 9)
        productos.add(new ProductoDTO(9, 3, "Rocoto Relleno", "Rocoto relleno con carne molida y queso gratinado", 190, true, "/imagenes/rocoto.jpg"));
        
 */
        return productos;
    }

}
