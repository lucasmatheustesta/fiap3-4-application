package com.fiap.trabalho1.fiap.external.request;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRequest {

	private String name;
    private BigDecimal value;
    private String description;
    private UUID categoryId;

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
