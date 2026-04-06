package com.jsport.backend_ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference; // IMPORTANTE
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List; // IMPORTANTE

@Entity
@Table(name = "products", schema = "\"JSPORT\"")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal base_price;

    @Column(nullable = false)
    private BigDecimal price;

    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private boolean active = true;

    @Column(nullable = false)
    private Integer stock;

    // --- NUEVA CONEXIÓN A LAS IMÁGENES ---
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference // Indica que Product es el "dueño" de la relación en el JSON
    private List<ImageProducts> images;
}