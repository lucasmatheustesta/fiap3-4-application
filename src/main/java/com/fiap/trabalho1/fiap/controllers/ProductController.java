package com.fiap.trabalho1.fiap.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.external.request.ProductRequest;
import com.fiap.trabalho1.fiap.usecases.product.CreateProductUseCase;
import com.fiap.trabalho1.fiap.usecases.product.ListAllProductsUseCase;

@RestController
@RequestMapping("/products")
public class ProductController {
	
    private final CreateProductUseCase createProductUseCase;
    private final ListAllProductsUseCase listAllProductsUseCase;
    
    public ProductController(CreateProductUseCase createProductUseCase,
    						 ListAllProductsUseCase listAllProductsUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.listAllProductsUseCase = listAllProductsUseCase;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest request) {
        Product product = createProductUseCase.execute(
                request.getName(),
                request.getValue(),
                request.getDescription(),
                request.getCategoryId()
        );
        return ResponseEntity.ok(product);
    }
    
    @GetMapping
	public ResponseEntity<Page<Product>> listAllProdcuts(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
														 @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
    	Page<Product> products = this.listAllProductsUseCase.listAllProdcuts(page, size);
    	if (products.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	return ResponseEntity.ok(products);
	}
    
}
