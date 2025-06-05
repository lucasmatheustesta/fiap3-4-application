package com.fiap.trabalho1.fiap.controllers;

import com.fiap.trabalho1.fiap.usecases.category.DeleteCategoryByIdUseCase;
import com.fiap.trabalho1.fiap.usecases.category.FindCategoryByIdUseCase;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.external.request.CategoryRequest;
import com.fiap.trabalho1.fiap.usecases.category.CreateCateogryUseCase;
import com.fiap.trabalho1.fiap.usecases.category.ListAllCategoriesUseCase;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private ListAllCategoriesUseCase listAllCategoriesUseCase;
	
	@Autowired
	private CreateCateogryUseCase createCateogryUseCase;

	@Autowired
	private FindCategoryByIdUseCase findCategoryByIdUseCase;

	@Autowired
	private DeleteCategoryByIdUseCase deleteCategoryByIdUseCase;

	@PostMapping
	public ResponseEntity<?> saveCategory(@RequestBody CategoryRequest request) {
		Category categorySaved = this.createCateogryUseCase.execute(request.getName(), request.getType());
		return ResponseEntity.ok(categorySaved);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> findCategoryById(@PathVariable String id) {
		Optional<Category> categoryFound = this.findCategoryByIdUseCase.execute(UUID.fromString(id));
		if (categoryFound.isPresent()) {
			return ResponseEntity.ok(categoryFound.get());
		}
		return ResponseEntity.notFound().build();
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

	@DeleteMapping("/{id}")
	public HttpStatus deleteCategoryById(@PathVariable String id) {
		this.deleteCategoryByIdUseCase.execute(UUID.fromString(id));
		return HttpStatus.OK;
	}

}
