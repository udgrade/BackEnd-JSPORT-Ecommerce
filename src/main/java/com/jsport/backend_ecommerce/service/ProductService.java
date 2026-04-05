package com.jsport.backend_ecommerce.service;

import com.jsport.backend_ecommerce.entity.Product;
import com.jsport.backend_ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 1. OBTENER TODOS LOS PRODUCTOS
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. OBTENER UN PRODUCTO POR ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 3. CREAR UN NUEVO PRODUCTO
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 4. ACTUALIZAR UN PRODUCTO EXISTENTE
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPrice(productDetails.getPrice());
                    existingProduct.setStock(productDetails.getStock());

                    // Si manejas categorías, asegúrate de pasar el objeto categoría completo
                    if (productDetails.getCategory() != null) {
                        existingProduct.setCategory(productDetails.getCategory());
                    }

                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el id: " + id));
    }

    // 5. ELIMINAR UN PRODUCTO
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El producto con id " + id + " no existe.");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void reduceStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("No hay suficiente stock para el producto: " + product.getName());
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
}