package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.Categoria;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.security.UsuarioDetails;
import com.example.Ejemplo.services.CategoriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaServiceImpl categoriaService;

    @Autowired
    public CategoriaController(CategoriaServiceImpl categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Muestra la página principal de gestión de categorías
     */
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal UsuarioDetails userDetails) {
        Usuario usuario = userDetails.getUsuario();
        
        List<Categoria> categorias = categoriaService.findAll();
        
        model.addAttribute("usuarioAdmins", usuario.getRol().toString());
        model.addAttribute("usuarioNombre", usuario.getNombre());
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", new Categoria());
        
        return "administrador/categorias";
    }

    /**
     * Guarda o actualiza una categoría
     */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria, 
                         RedirectAttributes redirectAttributes) {
        try {
            categoriaService.save(categoria);
            String mensaje = categoria.getIdCategoria() == null ? 
                "Categoría creada correctamente" : "Categoría actualizada correctamente";
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar la categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        
        return "redirect:/categorias";
    }

    /**
     * Carga una categoría para editar
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, 
                        @AuthenticationPrincipal UsuarioDetails userDetails) {
        Usuario usuario = userDetails.getUsuario();
        
        Categoria categoria = categoriaService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        
        List<Categoria> categorias = categoriaService.findAll();
        
        model.addAttribute("usuarioAdmins", usuario.getRol().toString());
        model.addAttribute("usuarioNombre", usuario.getNombre());
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", categoria);
        model.addAttribute("modoEdicion", true);
        
        return "administrador/categorias";
    }

    /**
     * Elimina una categoría
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            long cantidadProductos = categoriaService.countProductosByCategoria(id);
            
            if (cantidadProductos > 0) {
                redirectAttributes.addFlashAttribute("mensaje", 
                    "No se puede eliminar la categoría porque tiene " + cantidadProductos + " producto(s) asociado(s)");
                redirectAttributes.addFlashAttribute("tipoMensaje", "warning");
            } else {
                categoriaService.deleteById(id);
                redirectAttributes.addFlashAttribute("mensaje", "Categoría eliminada correctamente");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar la categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        
        return "redirect:/categorias";
    }

    /**
     * API REST para obtener todas las categorías (útil para AJAX)
     */
    @GetMapping("/api/listar")
    @ResponseBody
    public List<Categoria> listarCategorias() {
        return categoriaService.findAll();
    }

    /**
     * API REST para verificar si existe una categoría con el nombre dado
     */
    @GetMapping("/api/existe")
    @ResponseBody
    public boolean existeCategoria(@RequestParam String nombre) {
        return categoriaService.existsByNombre(nombre);
    }
}
