package com.jsport.backend_ecommerce.service;

import com.jsport.backend_ecommerce.entity.Category;
import com.jsport.backend_ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. OBTENER TODAS LAS CATEGORÍAS
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 2. OBTENER CATEGORÍA POR ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // 3. CREAR CATEGORÍA
    @Transactional
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // 4. ACTUALIZAR CATEGORÍA
    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(categoryDetails.getName());
                    // Si la entidad tiene descripción u otros campos, se agregan:
                    // existingCategory.setDescription(categoryDetails.getDescription());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el id: " + id));
    }

    // 5. ELIMINAR CATEGORÍA
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: La categoría no existe.");
        }

        // NOTA: Si una categoría tiene productos asociados, esto podría dar error
        // de integridad referencial en Postgres. Es el comportamiento correcto.
        categoryRepository.deleteById(id);
    }
}