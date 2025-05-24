package com.fiap.trabalho1.fiap.usecases.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ListAllCategoriesUseCase {

	private final CategoryRepository categoryRepository;

    public ListAllCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public Page<Category> listAllCategories(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
		return this.categoryRepository.findAll(pageRequest);
	}
    
}
