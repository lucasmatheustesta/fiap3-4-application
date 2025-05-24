package com.fiap.trabalho1.fiap.entities;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
    
	@Id
    private UUID id;
    private String name;
    private BigDecimal value;
    private String description;
//    private UUID categoryId;

    @ManyToOne(optional=false)
	private Category category;
    
    public Product() {}

    public Product(UUID idProduct, String name, BigDecimal value, String description, Category category) {
    	this.id = idProduct;
    	this.name = name;
    	this.value = value;
    	this.description = description;
    	this.category = category; 
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
