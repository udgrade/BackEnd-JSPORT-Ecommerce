package com.jsport.backend_ecommerce.controller;

import com.jsport.backend_ecommerce.entity.Category;
import com.jsport.backend_ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 1. Obtener todas las categorías
    @GetMapping
    public List<Category> getAllCategories(){
        // Usamos el servicio en lugar del repositorio directamente para mantener el orden
        return categoryService.getAllCategories();
    }

    // 2. Crear una nueva categoría
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    // 3. ACTUALIZAR CATEGORÍA
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        // Asegúrate de que el nombre del método coincida con el del Service (updateCategory)
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // 4. ELIMINAR CATEGORÍA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        // Asegúrate de que el nombre del método coincida con el del Service (deleteCategory)
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}