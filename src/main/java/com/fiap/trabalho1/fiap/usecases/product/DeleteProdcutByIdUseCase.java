package com.fiap.trabalho1.fiap.usecases.product;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.gateways.ProductRepository;

@Service
public class DeleteProdcutByIdUseCase {

	private final ProductRepository productRepository;
	
	public DeleteProdcutByIdUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(UUID productId) {
    	this.productRepository.deleteById(productId);
    }
    
}
