package com.jsport.backend_ecommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    // Inyectamos los valores desde application.properties por seguridad
    public CloudinaryService(
            @Value("${cloudinary.cloud_name}") String cloudName,
            @Value("${cloudinary.api_key}") String apiKey,
            @Value("${cloudinary.api_secret}") String apiSecret) {

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }

    /**
     * Borra una imagen de Cloudinary usando su Public ID.
     * @param publicId El ID único de la imagen (ej: "jsport/zapato_123")
     * @return El resultado de la operación ("ok" si fue exitoso)
     */
    public String deleteImage(String publicId) {
        try {
            if (publicId == null || publicId.isEmpty()) return "id_null";

            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return result.get("result").toString(); // Retorna "ok" o "not found"

        } catch (IOException e) {
            System.err.println("Error al borrar imagen en Cloudinary: " + e.getMessage());
            return "error";
        }
    }
}