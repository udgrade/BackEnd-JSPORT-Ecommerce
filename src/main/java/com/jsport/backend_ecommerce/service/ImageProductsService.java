package com.jsport.backend_ecommerce.service;

import com.jsport.backend_ecommerce.entity.ImageProducts;
import com.jsport.backend_ecommerce.repository.ImagesProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ImageProductsService {

    @Autowired
    private ImagesProductsRepository imageRepository;

    @Transactional
    public List<ImageProducts> saveImagesBulk(List<ImageProducts> imagesList) {
        if (imagesList.isEmpty()) return imagesList;

        // 1. Obtenemos el ID del producto (asumimos que todas las fotos son del mismo)
        Long productId = imagesList.get(0).getProduct().getId();

        // 2. ¿Alguna de las nuevas fotos es primaria?
        boolean containsNewPrimary = imagesList.stream().anyMatch(ImageProducts::isPrimary);

        // 3. Si hay una nueva primaria, reseteamos las que ya existían en la BD
        if (containsNewPrimary) {
            imageRepository.resetPrimaryStatus(productId);
        }

        // 4. Guardamos el lote completo
        return imageRepository.saveAll(imagesList);
    }
}