package com.fiap.trabalho1.fiap.usecases.product;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;

@Service
public class FindProdcutByIdUseCase {

	private final ProductRepository productRepository;
	
	public FindProdcutByIdUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> execute(UUID productId) {
        return this.productRepository.findById(productId);
    }
    
}
