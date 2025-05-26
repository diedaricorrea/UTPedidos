package com.example.Ejemplo.interfaces;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Ejemplo.models.Plato;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class PlatoServiceImpl implements PlatoService {

    private List<Plato> platos = new ArrayList<>();
    private final Path rootLocation = Paths.get("src/main/resources/static/images/"); // agregar la ruta de la carpeta
                                                                                      // images en el proyecto
    private final Path jsonPath = Paths.get("src/main/resources/static/data/platos.json");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Long nextId = 1L;

    @PostConstruct
    public void init() {
        try {
            // Crear directorios si no existen
            Files.createDirectories(rootLocation);
            Files.createDirectories(jsonPath.getParent());

            // Verificar si existe el archivo JSON
            File jsonFile = jsonPath.toFile();
            if (jsonFile.exists() && jsonFile.length() > 0) {
                // Cargar platos desde JSON solo si el archivo no está vacío
                platos = objectMapper.readValue(jsonFile, new TypeReference<List<Plato>>() {
                });
                // Encontrar el próximo ID
                nextId = platos.stream()
                        .map(Plato::getId)
                        .max(Comparator.naturalOrder())
                        .orElse(0L) + 1;
            } else {
                // Si el archivo no existe o está vacío, crear uno con una lista vacía
                if (!jsonFile.exists()) {
                    jsonFile.createNewFile();
                }
                // Inicializar con una lista vacía y escribirla al archivo
                platos = new ArrayList<>();
                objectMapper.writeValue(jsonFile, platos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el servicio de platos", e);
        }
    }

    private void saveToJson() {
        try {
            objectMapper.writeValue(jsonPath.toFile(), platos);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar platos en JSON", e);
        }
    }

    @Override
    public List<Plato> findAll() {
        return new ArrayList<>(platos);
    }

    @Override
    public List<Plato> findRecent() {
        return platos.stream()
                .sorted(Comparator.comparing(Plato::getId).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public Plato findById(Long id) {
        return platos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Plato save(Plato plato, MultipartFile imagen) {
        // Manejar la imagen si se proporciona
        if (imagen != null && !imagen.isEmpty()) {
            String filename = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename();
            try {
                Files.copy(imagen.getInputStream(), this.rootLocation.resolve(filename));
                plato.setImagenUrl("/images/platos/" + filename);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen: " + e.getMessage());
            }
        }

        // Si es un nuevo plato, asignar ID
        if (plato.getId() == null) {
            plato.setId(nextId++);
            platos.add(plato);
        } else {
            // Actualizar plato existente
            for (int i = 0; i < platos.size(); i++) {
                if (platos.get(i).getId().equals(plato.getId())) {
                    if (plato.getImagenUrl() == null && (imagen == null || imagen.isEmpty())) {
                        plato.setImagenUrl(platos.get(i).getImagenUrl());
                    }
                    platos.set(i, plato);
                    break;
                }
            }
        }

        // Guardar cambios en el archivo JSON
        saveToJson();

        return plato;
    }

    @Override
    public void delete(Long id) {
        platos.removeIf(p -> p.getId().equals(id));
        saveToJson();
    }

    @Override
    public List<Plato> findByCategoria(String categoria) {
        return platos.stream()
                .filter(p -> p.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }
}