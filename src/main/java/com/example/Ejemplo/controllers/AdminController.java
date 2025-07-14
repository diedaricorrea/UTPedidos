package com.example.Ejemplo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Ejemplo.services.ProductoService;
import com.example.Ejemplo.models.Categoria;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.repository.CategoriaRepository;
import com.example.Ejemplo.repository.DetalleVentaRepository;

@Controller
@RequestMapping("/productos")
public class AdminController {

    private final ProductoService productoService;

    private final CategoriaRepository categoriaRepository;


    @Autowired
    public AdminController(ProductoService productoService, CategoriaRepository categoriaRepository) {
        this.productoService = productoService;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String panelAdmin(Model model) {
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos en la base de datos.");
        } else {
            System.out.println("Productos encontrados: " + productos.size());
            for (Producto p : productos) {
                System.out.println("Producto: " + p.getNombre() + ", Categoria: " + (p.getCategoria() != null ? p.getCategoria().getNombre() : "null"));
            }
        }
        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto());
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "administrador/estadisticasProductos";
    }

    @PostMapping("/subirproductos")
    public String guardarProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("categoria") String categoriaNombre,
            @RequestParam("imagen") MultipartFile imagen,
            @RequestParam(value = "disponible", defaultValue = "false") boolean disponible,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("stock") Integer stock,
            RedirectAttributes redirectAttributes) {
        Producto producto = new Producto();
        if (id != null) {
            producto.setIdProducto(id);
        }
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        Categoria categoria = categoriaRepository.findByNombre(categoriaNombre);
        if (categoria == null) {
            categoria = new Categoria();
            categoria.setNombre(categoriaNombre);
            categoria = categoriaRepository.save(categoria);
        }
        producto.setCategoria(categoria);
        producto.setEstado(disponible);
        producto.setStock(stock);
        productoService.save(producto, imagen);
        redirectAttributes.addFlashAttribute("mensaje", "Producto guardado correctamente");
        return "redirect:/productos";
    }

    @GetMapping("/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = productoService.findById(id);
        if (producto != null) {
            model.addAttribute("producto", producto);
            model.addAttribute("productos", productoService.findRecent());
            List<Categoria> categorias = categoriaRepository.findAll();
            model.addAttribute("categorias", categorias);
            return "panelAdmin";
        }
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.findById(id);
        if (producto != null) {
            producto.setEstado(false);
            productoService.save(producto, null); // O un método específico para actualizar solo el estado
            redirectAttributes.addFlashAttribute("mensaje", "Producto marcado como no disponible");
        }
        return "redirect:/productos";
    }
}

