package com.example.Ejemplo.controllers;

import java.util.List;

import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.security.UsuarioDetails;
import com.example.Ejemplo.services.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Ejemplo.models.Categoria;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.repository.CategoriaRepository;

@Controller
@RequestMapping("/productos")
public class AdminController {

    private final ProductoServiceImpl productoServiceImpl;

    private final CategoriaRepository categoriaRepository;


    @Autowired
    public AdminController(ProductoServiceImpl productoServiceImpl, CategoriaRepository categoriaRepository) {
        this.productoServiceImpl = productoServiceImpl;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String panelAdmin(Model model, @AuthenticationPrincipal UsuarioDetails userDetails) {
        Usuario usuario = userDetails.getUsuario();

        List<Producto> productos = productoServiceImpl.findAll();
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos en la base de datos.");
        } else {
            System.out.println("Productos encontrados: " + productos.size());
            for (Producto p : productos) {
                System.out.println("Producto: " + p.getNombre() + ", Categoria: " + (p.getCategoria() != null ? p.getCategoria().getNombre() : "null"));
            }
        }
        model.addAttribute("usuarioAdmins", usuario.getRol().toString());
        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias",categoriaRepository.findAll());
        return "administrador/productos";
    }

    @GetMapping("/edicion")
    public String edicion(Model model, @AuthenticationPrincipal UsuarioDetails userDetails) {
        Usuario usuario = userDetails.getUsuario();
        model.addAttribute("usuarioAdmins", usuario.getRol().toString());
        model.addAttribute("productos", productoServiceImpl.findAll());
        return  "administrador/accionesProducto";
    }

    @GetMapping("/search")
    public String buscar(Model model,@RequestParam("nombre") String nombre) {
        model.addAttribute("productos", productoServiceImpl.obtenerProductosIgnore(nombre));
        return  "administrador/accionesProducto";
    }

    @PostMapping("/actualizar")
    public String actualizar(RedirectAttributes redirectAttribute,
                             @RequestParam("idProducto") Integer idProducto,
                             @RequestParam("nombre") String nombre,
                             @RequestParam("precio") double precio,
                             @RequestParam("descripcion") String descripcion,
                             @RequestParam("stock") Integer stock) {
        Producto producto = productoServiceImpl.findById(idProducto);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setStock(producto.getStock() + stock);
        productoServiceImpl.saveProduct(producto);
        redirectAttribute.addFlashAttribute("message","El producto se actualizo correctamente");
        return  "redirect:/productos/edicion";
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
        productoServiceImpl.save(producto, imagen);
        redirectAttributes.addFlashAttribute("mensaje", "Producto guardado correctamente");
        return "redirect:/productos";
    }

    @GetMapping("/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = productoServiceImpl.findById(id);
        if (producto != null) {
            model.addAttribute("producto", producto);
            model.addAttribute("productos", productoServiceImpl.findRecent());
            List<Categoria> categorias = categoriaRepository.findAll();
            model.addAttribute("categorias", categorias);
            return "administrador/productos";
        }
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Producto producto = productoServiceImpl.findById(id);
        if (producto != null) {
            producto.setEstado(false);
            productoServiceImpl.save(producto, null); // O un método específico para actualizar solo el estado
            redirectAttributes.addFlashAttribute("mensaje", "Producto marcado como no disponible");
        }
        return "redirect:/productos";
    }
}

