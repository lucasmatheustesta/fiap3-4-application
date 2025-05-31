package com.fiap.trabalho1.fiap.usecases.category;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;
import com.fiap.trabalho1.fiap.infrastructure.exceptions.BusinessValidationException;


@Service
public class CreateCateogryUseCase {

	private final CategoryRepository categoryRepository;
	
	public CreateCateogryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category execute(String name, String type) {
    	if (type == null || type.isEmpty()) {
    		throw new BusinessValidationException("Cateogry type must not be null or empty");
    	}
    	
    	Set<String> allowedTypes = Set.of("SANDWICH", "DRINK", "DESSERT", "SIDE_DISH");
    	if (!allowedTypes.contains(type)) {
    		throw new BusinessValidationException("Only are allowed: SANDWICH, DRINK, DESSERT, SIDE_DISH");
    	}
    	
    	if (name == null || name.isEmpty()) {
    		throw new BusinessValidationException("Name must not be null or empty");
    	}
    	
    	Category category = new Category(
                UUID.randomUUID(),
                name,
                type
        );
    	
        return categoryRepository.save(category);
    }
    
}
