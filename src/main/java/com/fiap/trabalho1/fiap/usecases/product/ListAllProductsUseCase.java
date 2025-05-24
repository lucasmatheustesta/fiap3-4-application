package com.fiap.trabalho1.fiap.usecases.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class ListAllProductsUseCase {
	
	private final ProductRepository productRepository;

	public ListAllProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	
	public Page<Product> listAllProdcuts(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
		return this.productRepository.findAll(pageRequest);
	}
	
}
