package com.example.Ejemplo.controllers;

import java.util.List;

import com.example.Ejemplo.models.Carrito;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.models.Rol;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.security.UsuarioDetails;
import com.example.Ejemplo.services.CarritoServiceImpl;
import com.example.Ejemplo.services.NotificacionServiceImpl;
import com.example.Ejemplo.services.ProductoServiceImpl;
import com.example.Ejemplo.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final ProductoServiceImpl productosServiceImpl;

    private final CarritoServiceImpl carritoServiceImpl;

    private final NotificacionServiceImpl notificacionServiceImpl;

    @Autowired
    public ProductoController(ProductoServiceImpl productosServiceImpl, CarritoServiceImpl carritoServiceImpl, NotificacionServiceImpl notificacionServiceImpl) {
        this.productosServiceImpl = productosServiceImpl;
        this.carritoServiceImpl = carritoServiceImpl;
        this.notificacionServiceImpl = notificacionServiceImpl;
    }

    @GetMapping()
    public String index(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int page,
            @AuthenticationPrincipal UsuarioDetails userDetails,
            Model model) {
        Usuario usuario = userDetails.getUsuario();

        model.addAttribute("usuarioAdmins", usuario.getRol().toString());
        model.addAttribute("usuarioNombre", usuario.getNombre());

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
        model.addAttribute("notificaciones",notificacionServiceImpl.findAllByUsuario_IdUsuario(usuario.getIdUsuario()));
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
            @AuthenticationPrincipal UsuarioDetails userDetails,
            @RequestParam int cantidad,
            RedirectAttributes redirectAttributes) {
        Usuario usuario = userDetails.getUsuario();
        int idUsuario = usuario.getIdUsuario();
        int nuevaCantidad = 0;
        double nuevoTotal = 0;
        Producto producto = productosServiceImpl.findProductoById(idProducto).orElse(null);

        if (producto == null) {
            redirectAttributes.addFlashAttribute("message", "Producto no encontrado.");
            return "redirect:/catalogo";
        }

        if (cantidad > producto.getStock()) {
            redirectAttributes.addFlashAttribute("message", "El stock no puede ser menor que el stock.");
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
                    return "redirect:/catalogo";
                }
            }
        }

        carritoServiceImpl.saveCarrito(idUsuario, idProducto, cantidad, total);
        redirectAttributes.addFlashAttribute("message", "Se añadió al carrito correctamente");
        return "redirect:/catalogo";
    }


}