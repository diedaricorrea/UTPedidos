package com.example.Ejemplo.controllers;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Ejemplo.interfaces.PlatoService;
import com.example.Ejemplo.models.Plato;

@Controller
@RequestMapping("/platos")
public class AdminController {

    private final PlatoService platoService;

    public AdminController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @GetMapping
    public String panelAdmin(Model model) {
        model.addAttribute("platos", platoService.findRecent());
        model.addAttribute("plato", new Plato());
        return "platos";
    }

    @PostMapping("/subirplatos")
    public String guardarPlato(@RequestParam("nombre") String nombre,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("categoria") String categoria,
            @RequestParam("imagen") MultipartFile imagen,
            @RequestParam(value = "disponible", defaultValue = "false") boolean disponible,
            @RequestParam(value = "id", required = false) Long id,
            RedirectAttributes redirectAttributes) {

        Plato plato = new Plato();
        plato.setId(id);
        plato.setNombre(nombre);
        plato.setPrecio(precio);
        plato.setDescripcion(descripcion);
        plato.setCategoria(categoria);
        plato.setDisponible(disponible);

        platoService.save(plato, imagen);

        redirectAttributes.addFlashAttribute("mensaje", "Plato guardado correctamente");
        return "redirect:/platos";
    }

    @GetMapping("/{id}")
    public String editarPlato(@PathVariable Long id, Model model) {
        Plato plato = platoService.findById(id);
        model.addAttribute("plato", plato);
        model.addAttribute("platos", platoService.findRecent());
        return "panelAdmin";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPlato(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        platoService.delete(id);
        redirectAttributes.addFlashAttribute("mensaje", "Plato eliminado correctamente");
        return "redirect:/platos";
    }
}
