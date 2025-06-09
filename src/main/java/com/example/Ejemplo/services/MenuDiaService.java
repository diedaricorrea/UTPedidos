package com.example.Ejemplo.services;

import com.example.Ejemplo.models.MenuDia;
import com.example.Ejemplo.models.Producto;
import com.example.Ejemplo.repository.MenuDiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MenuDiaService {
    List<MenuDia> findAllMenuDias();
    MenuDia saveMenudia(MenuDia menu);
}
