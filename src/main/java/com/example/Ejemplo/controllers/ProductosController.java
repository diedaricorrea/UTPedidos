package com.example.Ejemplo.controllers;


import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.services.ProductosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ProductosController {
    @Autowired
    public ProductosServiceImpl productosServiceImpl;

    @GetMapping("/productos")
    public String index(Model model) {
        model.addAttribute("productos", productosServiceImpl.findAllProductos());
        return "productos";
    }

    @GetMapping("/prueba")
    public String prueba(Model model) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        model.addAttribute("usuario",usuario.getIdUsuario());
        return "carta";
    }



}
