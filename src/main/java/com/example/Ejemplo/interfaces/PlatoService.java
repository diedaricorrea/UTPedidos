package com.example.Ejemplo.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.Ejemplo.models.Plato;

public interface PlatoService {
    List<Plato> findAll();

    List<Plato> findRecent();

    Plato findById(Long id);

    Plato save(Plato plato, MultipartFile imagen);

    void /* 👀👀👀👀👀👀 */ delete(Long id);

    List<Plato> findByCategoria(String categoria);
}