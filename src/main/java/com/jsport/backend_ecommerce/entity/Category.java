package com.jsport.backend_ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categories", schema = "\"JSPORT\"")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean active = true;
}