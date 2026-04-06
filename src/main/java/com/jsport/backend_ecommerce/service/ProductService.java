package com.jsport.backend_ecommerce.service;

import com.jsport.backend_ecommerce.entity.ImageProducts;
import com.jsport.backend_ecommerce.entity.Product;
import com.jsport.backend_ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product saveProduct(Product product) {
        // Asegurar que las imágenes conozcan a su producto padre antes de guardar
        if (product.getImages() != null) {
            product.getImages().forEach(img -> img.setProduct(product));
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // 1. GESTIÓN DE IMÁGENES (Sincronización con Cloudinary)
                    if (productDetails.getImages() != null) {

                        // Identificamos qué imágenes estaban antes pero ya no vienen en la petición (fueron borradas en el front)
                        List<ImageProducts> imagesToRemove = existingProduct.getImages().stream()
                                .filter(oldImg -> productDetails.getImages().stream()
                                        .noneMatch(newImg -> newImg.getUrl().equals(oldImg.getUrl())))
                                .collect(Collectors.toList());

                        // Borrado físico en Cloudinary (Opcional pero recomendado)
                        for (ImageProducts img : imagesToRemove) {
                            String publicId = extractPublicIdFromUrl(img.getUrl());
                            System.out.println("Solicitando borrado en Cloudinary para: " + publicId);
                            cloudinaryService.deleteImage(publicId);
                        }

                        // Limpiamos la lista actual y añadimos las que vienen del front
                        // Gracias al orphanRemoval=true en la Entity, las que quitemos de aquí se borran de Neon
                        existingProduct.getImages().clear();

                        for (ImageProducts newImg : productDetails.getImages()) {
                            newImg.setProduct(existingProduct); // Volver a vincular para la FK
                            existingProduct.getImages().add(newImg);
                        }
                    }

                    // 2. ACTUALIZACIÓN DE CAMPOS BÁSICOS
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setBrand(productDetails.getBrand());
                    existingProduct.setBase_price(productDetails.getBase_price());
                    existingProduct.setPrice(productDetails.getPrice());
                    existingProduct.setStock(productDetails.getStock());
                    existingProduct.setActive(productDetails.isActive());

                    if (productDetails.getCategory() != null) {
                        existingProduct.setCategory(productDetails.getCategory());
                    }

                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el id: " + id));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe."));

        // Antes de borrar el producto, podrías querer borrar todas sus imágenes de Cloudinary
        for (ImageProducts img : product.getImages()) {
            cloudinaryService.deleteImage(extractPublicIdFromUrl(img.getUrl()));
        }

        productRepository.delete(product);
    }

    @Transactional
    public void reduceStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("No hay suficiente stock para: " + product.getName());
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    /**
     * Extrae el Public ID de una URL de Cloudinary.
     * Ejemplo: .../upload/v1234/jsport/zapato.jpg -> jsport/zapato
     */
    private String extractPublicIdFromUrl(String url) {
        try {
            if (url == null || !url.contains("/upload/")) return null;
            String parts[] = url.split("/upload/");
            String path = parts[1].substring(parts[1].indexOf("/") + 1); // Quita la versión (v1234...)
            return path.substring(0, path.lastIndexOf(".")); // Quita la extensión (.jpg)
        } catch (Exception e) {
            return null;
        }
    }
}