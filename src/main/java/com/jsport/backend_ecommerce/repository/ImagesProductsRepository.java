package com.jsport.backend_ecommerce.repository;

import com.jsport.backend_ecommerce.entity.ImageProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ImagesProductsRepository extends JpaRepository<ImageProducts, Long> {

    List<ImageProducts> findByProductId(Long productId);

    // Query personalizada para quitar la "primicia" a todas las fotos de un producto
    @Modifying
    @Query("UPDATE ImageProducts i SET i.isPrimary = false WHERE i.product.id = :productId")
    void resetPrimaryStatus(@Param("productId") Long productId);
}