package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.MenuDia;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.repository.ProductosRepository;
import com.example.Ejemplo.services.MenuDiaServiceImpl;
import com.example.Ejemplo.services.ProductosServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MenuDiaController {
    @Autowired
    private ProductosServiceImpl productosServiceImpl;
    @Autowired
    private MenuDiaServiceImpl menuDiaServiceImpl;

    @GetMapping("/menuDia")
    public String menuDia(Model model) {
        List<Producto> productosMenuEconomico = productosServiceImpl.findAllByCategoriaNombre("MENU ECONOMICO");
        model.addAttribute("menusEconomicos",productosMenuEconomico);
        return "menuDia";
    }

    @PostMapping("/menuDia/guardar/")
    public String guardar(@Valid @ModelAttribute MenuDia menuDia, RedirectAttributes redirectAttributes, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "menuDia";
        }
        menuDiaServiceImpl.saveMenudia(menuDia);
        redirectAttributes.addFlashAttribute("mensaje","Menu Economico guardado correctamente");
        return "redirect:/menuDia";
    }
}
