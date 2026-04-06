package com.jsport.backend_ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // IMPORTANTE
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_images", schema = "\"JSPORT\"")
@Data
public class ImageProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(name = "is_primary")
    @JsonProperty("isPrimary")
    private boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference // Corta la recursión infinita hacia atrás
    private Product product;
}