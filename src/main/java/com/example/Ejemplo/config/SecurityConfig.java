package com.example.Ejemplo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/register", "/catalogo", "/login", "/register/save", "/ventas","/css/**", "/js/**").permitAll()

                    .requestMatchers("/catalogo/**", "/carrito/**",
                            "/pedidos","/pedidos/pedir","/notificacion").hasAnyRole("ADMINISTRADOR", "USUARIO","TRABAJADOR")

                    .requestMatchers("/productos/**", "/api/estadisticas/**",
                            "/categorias/**","/notificacion/notificar","/notificacion", "/pedidos","/pedidos/**",
                            "/usuarios/**","/usuarios/save","/usuarios/panelAdmin",
                            "/menuDia/**","/login/**").hasAnyRole("ADMINISTRADOR","TRABAJADOR")
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/login2",true)
            ).logout(logout -> logout.logoutSuccessUrl("/login?logout"));


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
