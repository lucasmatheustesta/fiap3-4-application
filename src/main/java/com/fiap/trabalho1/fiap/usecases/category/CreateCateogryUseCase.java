package com.fiap.trabalho1.fiap.usecases.category;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;


@Service
public class CreateCateogryUseCase {

	private final CategoryRepository categoryRepository;
	
	public CreateCateogryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category execute(String name, String type) {
    	Category category = new Category(
                UUID.randomUUID(),
                name,
                type
        );
    	
        return categoryRepository.save(category);
    }
    
}
