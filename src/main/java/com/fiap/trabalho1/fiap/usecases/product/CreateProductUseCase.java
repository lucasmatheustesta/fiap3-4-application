package com.fiap.trabalho1.fiap.usecases.product;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;
import com.fiap.trabalho1.fiap.infrastructure.exceptions.BusinessValidationException;

@Service
public class CreateProductUseCase {
    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(String name, BigDecimal value, String description, UUID categoryId) {
    	if (value.signum() != 1) {
    		throw new BusinessValidationException("Prodcut value must be a positive value");
    	}
    	
    	if (name == null || name.isEmpty()) {
    		throw new BusinessValidationException("Prodcut name must not be null or empty");
    	}
    	
    	if (categoryId == null) {
    		throw new BusinessValidationException("Cateogy id must not be null");
    	}
    	
        Product product = new Product(
                UUID.randomUUID(),
                name,
                value,
                description,
                categoryId
        );
        
        return productRepository.save(product);
    }
}