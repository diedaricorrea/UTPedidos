package com.example.Ejemplo.security;

import com.example.Ejemplo.models.Rol;
import com.example.Ejemplo.models.Usuario;
import com.example.Ejemplo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String correoAdmin = "admin@cafeteria.com";

        // Verifica si ya existe
        Optional<Usuario> adminExistente = usuarioRepository.findByCorreo(correoAdmin);
        if (adminExistente.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreo(correoAdmin);
            admin.setPassword(passwordEncoder.encode("admin123")); // Puedes cambiarla luego
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setFechaIngreso(LocalDateTime.now());
            admin.setEstado(true);

            usuarioRepository.save(admin);
            System.out.println("✅ Usuario ADMINISTRADOR creado con éxito.");
        } else {
            System.out.println("ℹ️ Usuario ADMINISTRADOR ya existe.");
        }
    }
}
