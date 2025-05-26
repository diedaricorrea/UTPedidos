package com.example.Ejemplo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VentaController {
    @GetMapping("/ventas")
    public String ventas() {
        return "ventas";
    }
}
