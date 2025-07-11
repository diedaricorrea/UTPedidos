package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.services.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductosController {
    @Autowired
    public ProductoServiceImpl productosServiceImpl;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("productos", productosServiceImpl.findAllProductos());
        return "administrador/productos";
    }


    @GetMapping("/producto")
    public List<Producto> producto() {
        return productosServiceImpl.findAllProductos();
    }

    @GetMapping("/prueba")
    public String prueba(Model model) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        model.addAttribute("usuario", usuario.getIdUsuario());
        return "administrador/carta";
    }

}
