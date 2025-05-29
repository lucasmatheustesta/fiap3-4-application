package com.fiap.trabalho1.fiap.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.external.request.CategoryRequest;
import com.fiap.trabalho1.fiap.usecases.category.CreateCateogryUseCase;
import com.fiap.trabalho1.fiap.usecases.category.ListAllCategoriesUseCase;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private ListAllCategoriesUseCase listAllCategoriesUseCase;
	
	@Autowired
	private CreateCateogryUseCase createCateogryUseCase;
	
	@PostMapping
	public ResponseEntity<?> salvarEvento(@RequestBody CategoryRequest request) {
		Set<String> allowedTypes = Set.of("SANDWICH", "DRINK", "DESSERT", "SIDE_DISH");

		if (!allowedTypes.contains(request.getType())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("Only are allowed: SANDWICH, DRINK, DESSERT, SIDE_DISH");
		}

		Category categorySaved = this.createCateogryUseCase.execute(request.getName(), request.getType());
		return ResponseEntity.ok(categorySaved);
	}
	
	@GetMapping
	public ResponseEntity<Page<Category>> listAllCategories(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
														    @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		Page<Category> categories = this.listAllCategoriesUseCase.listAllCategories(page, size);
    	if (categories.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	return ResponseEntity.ok(categories);
	}
	
}
