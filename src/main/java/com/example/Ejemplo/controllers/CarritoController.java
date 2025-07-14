package com.example.Ejemplo.controllers;

import com.example.Ejemplo.services.CarritoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoServiceImpl carritoServiceImpl;

    @Autowired
    public CarritoController(CarritoServiceImpl carritoServiceImpl) {
        this.carritoServiceImpl = carritoServiceImpl;
    }

    @GetMapping("/{idUsuario}")
    public String verCarrito(@PathVariable Integer idUsuario, Model model) {
        model.addAttribute("carrito", carritoServiceImpl.obtenerCarritosPorUsuario(idUsuario));
        return "usuario/carrito";
    }

    @PostMapping("/eliminar")
    public String eliminarDelCarrito(@RequestParam int idUsuario,
                                     @RequestParam int idProducto,
                                     RedirectAttributes redirectAttributes) {
        try {
            carritoServiceImpl.eliminarProductoAgregado(idUsuario,idProducto);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto: " + e.getMessage());
        }

        return "redirect:/carrito/1";
    }

}