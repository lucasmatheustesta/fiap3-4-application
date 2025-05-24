package com.fiap.trabalho1.fiap.usecases.product;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase {
    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(String name, BigDecimal value, String description, UUID categoryId) {
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