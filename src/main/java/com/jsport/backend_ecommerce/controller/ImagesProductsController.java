package com.jsport.backend_ecommerce.controller;

import com.jsport.backend_ecommerce.entity.ImageProducts;
import com.jsport.backend_ecommerce.service.ImageProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/images-products")
public class ImagesProductsController {

    @Autowired
    private ImageProductsService imageProductsService;

    @PostMapping("/bulk")
    public List<ImageProducts> createMultipleImages(@RequestBody List<ImageProducts> imagesList) {
        return imageProductsService.saveImagesBulk(imagesList);
    }
}