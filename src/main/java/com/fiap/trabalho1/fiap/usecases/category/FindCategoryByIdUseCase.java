package com.fiap.trabalho1.fiap.usecases.category;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;

@Service
public class FindCategoryByIdUseCase {

	private final CategoryRepository categoryRepository;

    public FindCategoryByIdUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
 
    public Optional<Category> execute(UUID categoryId) {
    	return this.categoryRepository.findById(categoryId);
    }
}
