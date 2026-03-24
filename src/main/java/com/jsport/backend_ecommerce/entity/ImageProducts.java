package com.jsport.backend_ecommerce.entity;

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
    @JsonProperty("isPrimary") // <--- ESTO FUERZA LA UNIÓN
    private boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
