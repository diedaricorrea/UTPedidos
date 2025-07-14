package com.example.Ejemplo.controllers;

import com.example.Ejemplo.models.Rol;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.services.NotificacionServiceImpl;
import com.example.Ejemplo.services.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuarioServiceImpl usuarioServiceImpl;
    private final NotificacionServiceImpl notificacionServiceImpl;

    @Autowired
    public UsuariosController(UsuarioServiceImpl usuarioServiceImpl, NotificacionServiceImpl notificacionServiceImpl) {
        this.usuarioServiceImpl = usuarioServiceImpl;
        this.notificacionServiceImpl = notificacionServiceImpl;
    }

    @GetMapping("/login")
    public String login(@RequestParam String correo, @RequestParam String password, Model model) {
        Usuario user = usuarioServiceImpl.isExistUsuario(correo, password);
        System.out.println(user.getRol());
        if(user.getRol() == Rol.ADMINISTRADOR){
            model.addAttribute("usuario",new Usuario());
            return "redirect:/usuarios/medio";
        }else{
            model.addAttribute("usuario",new Usuario());
            model.addAttribute("notificaciones", notificacionServiceImpl.findAllByUsuario_IdUsuario(1));
            return "redirect:/catalogo/";
        }
    }

    @GetMapping("/medio")
    public String medio() {
        return "login/medio";
    }


    @PostMapping("/save")
    public String saveUsuario(@ModelAttribute @Valid Usuario usuario,BindingResult resultado, RedirectAttributes redirectAttributes,Model model) {

        if(resultado.hasErrors()) {
            model.addAttribute("showModal", true); // 🔸 Esto activa el modal
            model.addAttribute("usuarios",usuarioServiceImpl.findAllUsuariosByNotRol(Rol.USUARIO));
            return "administrador/usuariosAdmin";
        }
        usuario.setEstado(true);
        usuarioServiceImpl.saveUser(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado correctamente");
        return "redirect:/usuarios/panelAdmin";
    }

    @GetMapping("/panelAdmin")
    public String panelAdmin(Model model) {
        model.addAttribute("usuario",new Usuario());
        model.addAttribute("usuarios",usuarioServiceImpl.findAllUsuariosByNotRol(Rol.USUARIO));
        return "administrador/usuariosAdmin";
    }

    @PostMapping("/update")
    public String updateUsuario(
            @RequestParam Integer actId,
            @RequestParam String actNombre,
            @RequestParam String actRol,
            @RequestParam String actEstado,
            RedirectAttributes redirectAttributes,
            Model model) {
        try{
            Usuario user = usuarioServiceImpl.findUsuarioById(actId).orElse(null);
            user.setNombre(actNombre.trim());
            user.setRol(Rol.valueOf(actRol.toUpperCase().trim()));
            actEstado.trim().toLowerCase();
            user.setEstado(Boolean.parseBoolean(actEstado));
            usuarioServiceImpl.saveUser(user);
        }catch (NullPointerException e){
            throw new RuntimeException(e);
        }

        redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        return "redirect:/usuarios/panelAdmin";
    }

    @GetMapping("/search")
    public String usuariosSearch(@RequestParam(required = false) Integer id, Model model) {
        if (id == null) {
            model.addAttribute("errorId", "El id no puede ser vacio");
            model.addAttribute("usuario", new Usuario());
            model.addAttribute("usuarios",usuarioServiceImpl.findAllUsuariosByNotRol(Rol.USUARIO));
            return "administrador/usuariosAdmin";
        }
        Usuario usuario = usuarioServiceImpl.findUsuarioById(id).orElse(null);
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios",usuario);

        return "administrador/usuariosAdmin";
    }
}
