package com.example.Ejemplo.controllers;

import java.util.List;

import com.example.Ejemplo.models.Carrito;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.services.CarritoServiceImpl;
import com.example.Ejemplo.services.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/catalogo")
public class ProductoController {

    @Autowired
    private ProductoServiceImpl productosServiceImpl;

    @Autowired
    private CarritoServiceImpl carritoServiceImpl;

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 6;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Producto> productosPage;

        if ((categoria != null && !categoria.isEmpty()) || (busqueda != null && !busqueda.isEmpty())) {
            productosPage = productosServiceImpl.buscarPorCategoriaYNombre(categoria, busqueda, pageable);
        } else {
            productosPage = productosServiceImpl.findAllProductosPaginado(pageable);
        }

        if (busqueda != null && !busqueda.isEmpty() && productosPage.isEmpty()) {
            model.addAttribute("noResultados", "Lo sentimos, no pudimos encontrar ese producto.");
        }
        model.addAttribute("productos", productosPage.getContent());
        model.addAttribute("productosPage", productosPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productosPage.getTotalPages());
        model.addAttribute("categoriaActual", categoria);
        model.addAttribute("busquedaActual", busqueda);
        model.addAttribute("noResultados", productosPage.getContent().isEmpty());

        return "usuario/catalogo";
    }

    @PostMapping("/subir")
    public String subirAlCarrito(
            @RequestParam Integer idProducto,
            @RequestParam Integer idUsuario,
            @RequestParam int cantidad,
            RedirectAttributes redirectAttributes) {

        int nuevaCantidad = 0;
        double nuevoTotal = 0;
        Producto producto = productosServiceImpl.findProductoById(idProducto).orElse(null);

        if (producto == null) {
            redirectAttributes.addFlashAttribute("message", "Producto no encontrado.");
            return "redirect:/catalogo";
        }

        double total = cantidad * producto.getPrecio();
        List<Carrito> carrito = carritoServiceImpl.obtenerCarritosPorUsuario(idUsuario);
        for (Carrito carro : carrito) {
            if (carro.getIdProducto().getIdProducto() == idProducto) {
                nuevaCantidad = carro.getCantidad() + cantidad;
                nuevoTotal = nuevaCantidad * carro.getIdProducto().getPrecio();
                if (carritoServiceImpl.actualizarProductoAgregado(idUsuario, idProducto, nuevaCantidad,
                        nuevoTotal) > 0) {
                    redirectAttributes.addFlashAttribute("message", "Se añadió al carrito correctamente");
                    return "redirect:/catalogo/";
                }
            }
        }

        carritoServiceImpl.saveCarrito(idUsuario, idProducto, cantidad, total);
        redirectAttributes.addFlashAttribute("message", "Se añadió al carrito correctamente");
        return "redirect:/catalogo/";
    }


}