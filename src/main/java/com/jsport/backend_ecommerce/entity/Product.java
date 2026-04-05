package com.jsport.backend_ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

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
    private BigDecimal base_price; // Usamos BigDecimal para dinero (pesos colombianos)

    @Column(nullable = false)
    private BigDecimal price; // Usamos BigDecimal para dinero (pesos colombianos)

    private String brand; // Marca (ejemplo: Nike, Adidas)

    @ManyToOne // Foranea: Muchos productos pertenecen a una categoría
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private boolean active = true;

    @Column(nullable = false)
    private Integer stock;

}
