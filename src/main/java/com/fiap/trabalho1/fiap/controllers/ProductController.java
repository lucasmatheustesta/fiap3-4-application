package com.fiap.trabalho1.fiap.controllers;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.external.request.ProductRequest;
import com.fiap.trabalho1.fiap.usecases.product.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
	
    private final CreateProductUseCase createProductUseCase;
    private final ListAllProductsUseCase listAllProductsUseCase;
    private final FindProdcutByIdUseCase findProdcutByIdUseCase;
    private final EditProductUseCase editProductUseCase;
    private final DeleteProdcutByIdUseCase deleteProdcutByIdUseCase;

    public ProductController(CreateProductUseCase createProductUseCase,
    						 ListAllProductsUseCase listAllProductsUseCase,
                             FindProdcutByIdUseCase findProdcutByIdUseCase,
                             EditProductUseCase editProductUseCase,
                             DeleteProdcutByIdUseCase deleteProdcutByIdUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.listAllProductsUseCase = listAllProductsUseCase;
        this.findProdcutByIdUseCase = findProdcutByIdUseCase;
        this.editProductUseCase = editProductUseCase;
        this.deleteProdcutByIdUseCase = deleteProdcutByIdUseCase;
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

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable String id) {
        Optional<Product> productOptional = this.findProdcutByIdUseCase.execute(UUID.fromString(id));
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> editProductById(@PathVariable String id, @RequestBody ProductRequest request) {
        UUID productId = UUID.fromString(id);
        if (this.findProdcutByIdUseCase.execute(productId).isPresent()) {
            Product product = editProductUseCase.execute(
                    productId,
                    request.getName(),
                    request.getValue(),
                    request.getDescription(),
                    request.getCategoryId()
            );
            return ResponseEntity.ok(product);
        }

        return ResponseEntity.notFound().build();
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

    @DeleteMapping("/{id}")
    public HttpStatus deleteCategoryById(@PathVariable String id) {
        this.deleteProdcutByIdUseCase.execute(UUID.fromString(id));
        return HttpStatus.OK;
    }

}
