package com.fiap.trabalho1.fiap.usecases.category;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.gateways.CategoryRepository;

@Service
public class DeleteCategoryByIdUseCase {

	private final CategoryRepository categoryRepository;

    public DeleteCategoryByIdUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public void execute(UUID categoryId) {
    	this.categoryRepository.deleteById(categoryId);
    }
}
