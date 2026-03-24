package com.jsport.backend_ecommerce.repository;

import com.jsport.backend_ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Consultas personalizadas fuera de los definidos por JPA
    //Metodo que se encarga de buscar los productos por categoria.
    List<Product> findByCategoryId(Long categoryId);

    // Metodo que se encarga de buscar productos por marca (Nike, Adidas, etc.)
    List<Product> findByBrand(String brand);
}
