package com.example.Ejemplo.services;

import com.example.Ejemplo.models.MenuDia;
import com.example.Ejemplo.repository.MenuDiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuDiaServiceImpl implements MenuDiaService {

    private final MenuDiaRepository menuDia;
    @Autowired
    public MenuDiaServiceImpl(MenuDiaRepository menuDia) {
        this.menuDia = menuDia;
    }

    @Override
    public List<MenuDia> findAllMenuDias() {
        return menuDia.findAll();
    }

    @Override
    public MenuDia saveMenudia(MenuDia menu) {
        return menuDia.save(menu);
    }
}
