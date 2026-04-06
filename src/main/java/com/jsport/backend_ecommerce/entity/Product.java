package com.jsport.backend_ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    // cascade = ALL + orphanRemoval = true es lo que permite que al quitar una imagen de la lista, se borre de la DB.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ImageProducts> images = new ArrayList<>();
}