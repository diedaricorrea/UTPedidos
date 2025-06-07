package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.Rol;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuariosController {

    @Autowired
    public UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios",usuarioRepository.findAll());
        return "usuariosAdmin";
    }

    @PostMapping("/usuarios/save/")
    public String saveUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        usuario.setEstado(true);
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado correctamente");
        return "redirect:/usuarios";
    }
}
