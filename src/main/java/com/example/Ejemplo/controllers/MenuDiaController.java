package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.MenuDia;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.services.MenuDiaServiceImpl;
import com.example.Ejemplo.services.ProductoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/menuDia")
public class MenuDiaController {
    private final ProductoServiceImpl productosServiceImpl;
    private final MenuDiaServiceImpl menuDiaServiceImpl;

    @Autowired
    public MenuDiaController(ProductoServiceImpl productosServiceImpl, MenuDiaServiceImpl menuDiaServiceImpl) {
        this.productosServiceImpl = productosServiceImpl;
        this.menuDiaServiceImpl = menuDiaServiceImpl;
    }

    @GetMapping("/")
    public String menuDia(Model model) {
        model.addAttribute("menusEconomicos", productosServiceImpl.findAllByCategoriaNombre("MENU ECONOMICO"));
        model.addAttribute("menuDia", new MenuDia());
        return "administrador/menuDia";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute MenuDia menuDia, RedirectAttributes redirectAttributes,
            BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "administrador/menuDia";
        }
        menuDiaServiceImpl.saveMenudia(menuDia);
        redirectAttributes.addFlashAttribute("mensaje", "Menu Economico guardado correctamente");
        return "redirect:/menuDia";
    }
}
