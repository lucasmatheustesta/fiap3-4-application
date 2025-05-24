package com.fiap.trabalho1.fiap.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.external.request.ProductRequest;
import com.fiap.trabalho1.fiap.usecases.product.CreateProductUseCase;
import com.fiap.trabalho1.fiap.usecases.product.DeleteProdcutByIdUseCase;
import com.fiap.trabalho1.fiap.usecases.product.EditProductUseCase;
import com.fiap.trabalho1.fiap.usecases.product.FindProdcutByIdUseCase;
import com.fiap.trabalho1.fiap.usecases.product.ListAllProductsUseCase;

@RestController
@RequestMapping("/products")
public class ProductController {
	
    private final CreateProductUseCase createProductUseCase;
    private final ListAllProductsUseCase listAllProductsUseCase;
    private final FindProdcutByIdUseCase findProdcutByIdUseCase;
    private final DeleteProdcutByIdUseCase deleteProdcutByIdUseCase;
    private final EditProductUseCase editProductUseCase;
    
    public ProductController(CreateProductUseCase createProductUseCase,
    						 ListAllProductsUseCase listAllProductsUseCase,
    						 FindProdcutByIdUseCase findProdcutByIdUseCase,
    						 DeleteProdcutByIdUseCase deleteProdcutByIdUseCase,
    						 EditProductUseCase editProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.listAllProductsUseCase = listAllProductsUseCase;
        this.findProdcutByIdUseCase = findProdcutByIdUseCase;
        this.deleteProdcutByIdUseCase = deleteProdcutByIdUseCase;
        this.editProductUseCase = editProductUseCase;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest request) {
        Product product = this.createProductUseCase.execute(
                request.getName(),
                request.getValue(),
                request.getDescription(),
                request.getCategoryId()
        );
        return ResponseEntity.ok(product);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable UUID id,
    										   @RequestBody ProductRequest request) {
    	if (this.findProdcutByIdUseCase.execute(id).isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	Product product = this.editProductUseCase.execute(
    			id,
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
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable UUID id) {
    	Optional<Product> prodcutFound = this.findProdcutByIdUseCase.execute(id);
    	if (prodcutFound.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	return ResponseEntity.ok(prodcutFound.get());
    }
    
    @DeleteMapping("/{id}")
    public HttpStatus deleteProductById(@PathVariable UUID id) {
    	Optional<Product> prodcutFound = this.findProdcutByIdUseCase.execute(id);
    	if (prodcutFound.isEmpty()) {
    		return HttpStatus.NOT_FOUND;
    	}
    	
    	this.deleteProdcutByIdUseCase.execute(id);
		return HttpStatus.OK;
    }
}
