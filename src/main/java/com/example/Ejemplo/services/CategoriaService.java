package com.example.Ejemplo.services;

import com.example.Ejemplo.models.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    
    /**
     * Obtiene todas las categorías
     */
    List<Categoria> findAll();
    
    /**
     * Busca una categoría por su ID
     */
    Optional<Categoria> findById(Integer id);
    
    /**
     * Busca una categoría por su nombre
     */
    Optional<Categoria> findByNombre(String nombre);
    
    /**
     * Guarda una nueva categoría o actualiza una existente
     */
    Categoria save(Categoria categoria);
    
    /**
     * Elimina una categoría por su ID
     */
    void deleteById(Integer id);
    
    /**
     * Verifica si existe una categoría con el nombre dado
     */
    boolean existsByNombre(String nombre);
    
    /**
     * Cuenta la cantidad de productos asociados a una categoría
     */
    long countProductosByCategoria(Integer idCategoria);
}
