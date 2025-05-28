package com.fiap.trabalho1.fiap.usecases.product;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;
import com.fiap.trabalho1.fiap.infrastructure.exceptions.BusinessValidationException;

@Service
public class EditProductUseCase {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
    public EditProductUseCase(ProductRepository productRepository,
							  CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product execute(UUID productId, String name, BigDecimal value, String description, UUID categoryId) {
    	Optional<Category> category = this.categoryRepository.findById(categoryId);
    	if (category.isEmpty()) {
    		throw new BusinessValidationException("Cateogria não encontrada");
    	}
    	
        Product product = new Product(
        		productId,
                name,
                value,
                description,
                category.get().getIdCateogry()
        );
        
        return this.productRepository.save(product);
    }
}
