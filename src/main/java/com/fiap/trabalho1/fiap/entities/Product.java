package com.fiap.trabalho1.fiap.entities;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    private UUID id;
    private String name;
    private BigDecimal value;
    private String description;
    private UUID categoryId;

    public Product() {}

    public Product(UUID idProduct, String name, BigDecimal value, String description, UUID categoryId) {
        this.id = idProduct;
        this.name = name;
        this.value = value;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Regras de negócio
    public void updateValue(BigDecimal newValue) {
        if (newValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The product value must be greater than zero.");
        }
        this.value = newValue;
    }

    // Getters e Setters
    public UUID getIdProduct() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }
}