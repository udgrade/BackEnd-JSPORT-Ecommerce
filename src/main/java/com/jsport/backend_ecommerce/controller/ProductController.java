package com.jsport.backend_ecommerce.controller;

import com.jsport.backend_ecommerce.entity.Product;
import com.jsport.backend_ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    //Metodo que obtiene todos los productos:
    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //Metodo que se encarga de crear un producto:
    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productRepository.save(product);
    }

    //Metodo que se encarga de buscar algun producto por marca:
    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand){
        return productRepository.findByBrand(brand);
    }

    //Metodo que se encarga de buscar algun producto por id de categoria haciendo alusion a su llave foranea
    @GetMapping("/category/{category}")
    public List<Product> getProductByCategories(@PathVariable Long category){
        return productRepository.findByCategoryId(category);
    }
}
