package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.Rol;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.repository.UsuarioRepository;
import com.example.Ejemplo.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UsuariosController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios",usuarioServiceImpl.findAllUsuarios());
        return "usuariosAdmin";
    }

    @PostMapping("/usuarios/save/")
    public String saveUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        usuario.setEstado(true);
        usuarioServiceImpl.saveUser(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado correctamente");
        return "redirect:/usuarios";
    }
    @PostMapping("/usuarios/delete/")
    public String deleteUsuario(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        usuarioServiceImpl.deleteUserById(id,false);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado correctamente");
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/update")
    public String updateUsuario(
            @RequestParam Integer actId,
            @RequestParam String actNombre,
            @RequestParam String actRol,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario user = usuarioServiceImpl.findUsuarioById(actId).orElse(null);
            user.setNombre(actNombre.trim());
            user.setRol(Rol.valueOf(actRol.toUpperCase().trim()));
            usuarioServiceImpl.saveUser(user);
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        return "redirect:/usuarios";
    }

    @GetMapping("/usuario/search/")
    public String usuariosSearch(@RequestParam Integer id, Model model) {
        model.addAttribute("usuarios",usuarioServiceImpl.findUsuarioById(id).orElse(null));
        return "usuariosAdmin";
    }
}
