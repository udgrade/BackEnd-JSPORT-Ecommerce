package com.jsport.backend_ecommerce.controller;

import com.jsport.backend_ecommerce.entity.Category;
import com.jsport.backend_ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Metodo Get definido para obtener todas las categorías
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    // 2. Crear una nueva categoría
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // ACTUALIZAR CATEGORÍA
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.update(id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // ELIMINAR CATEGORÍA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
