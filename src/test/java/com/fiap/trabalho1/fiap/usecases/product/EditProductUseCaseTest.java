package com.fiap.trabalho1.fiap.usecases.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;
import com.fiap.trabalho1.fiap.infrastructure.exceptions.BusinessValidationException;

class EditProductUseCaseTest {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private EditProductUseCase editProductUseCase;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        editProductUseCase = new EditProductUseCase(productRepository, categoryRepository);
    }

    @Test
    void shouldEditAndSaveProductWhenCategoryExists() {
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        String name = "Product1";
        BigDecimal value = BigDecimal.TEN;
        String description = "Description1";
        
        Category category = mock(Category.class);
        when(category.getIdCateogry()).thenReturn(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Product expectedProduct = new Product(productId, name, value, description, categoryId);
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        Product result = editProductUseCase.execute(productId, name, value, description, categoryId);

        assertNotNull(result);
        assertEquals(productId, result.getIdProduct());
        assertEquals(name, result.getName());
        assertEquals(value, result.getValue());
        assertEquals(description, result.getDescription());
        assertEquals(categoryId, result.getCategoryId());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenValueIsZero() {
    	UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        String name = "Product1";
        BigDecimal value = BigDecimal.TEN;
        String description = "Description1";
        
        Category category = mock(Category.class);
        when(category.getIdCateogry()).thenReturn(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Product expectedProduct = new Product(productId, name, value, description, categoryId);
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        
        BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> editProductUseCase.execute(productId, name, BigDecimal.ZERO,description, categoryId)
        );

        verify(productRepository, never()).save(any());
        assertEquals("Prodcut value must be a positive value", thrown.getMessage());   
    }
    
    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
    	UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        String name = "Product1";
        BigDecimal value = BigDecimal.TEN;
        String description = "Description1";
        
        Category category = mock(Category.class);
        when(category.getIdCateogry()).thenReturn(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Product expectedProduct = new Product(productId, name, value, description, categoryId);
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        
        BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> editProductUseCase.execute(productId, name, new BigDecimal("-10.00"),description, categoryId)
        );

        verify(productRepository, never()).save(any());
        assertEquals("Prodcut value must be a positive value", thrown.getMessage());   
    }
    
    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        BusinessValidationException exception = assertThrows(
                BusinessValidationException.class,
                () -> editProductUseCase.execute(
                        productId, "name", BigDecimal.ONE, "desc", categoryId)
        );
        assertEquals("Cateogria não encontrada", exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(productRepository, never()).save(any());
    }
    
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
    	UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        String name = "Product1";
        BigDecimal value = BigDecimal.TEN;
        String description = "Description1";
        
        Category category = mock(Category.class);
        when(category.getIdCateogry()).thenReturn(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Product expectedProduct = new Product(productId, name, value, description, categoryId);
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        
        BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> editProductUseCase.execute(productId, "", new BigDecimal("10.00"), description, categoryId)
        );

        verify(productRepository, never()).save(any());
        assertEquals("Prodcut name must not be null or empty", thrown.getMessage());
    }
    
    @Test
    void shouldThrowExceptionWhenNameIsNull() {
    	UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        String name = "Product1";
        BigDecimal value = BigDecimal.TEN;
        String description = "Description1";
        
        Category category = mock(Category.class);
        when(category.getIdCateogry()).thenReturn(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Product expectedProduct = new Product(productId, name, value, description, categoryId);
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        
    	
    	BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> editProductUseCase.execute(productId, null, new BigDecimal("10.00"), description, categoryId)
        );

        verify(productRepository, never()).save(any());
        assertEquals("Prodcut name must not be null or empty", thrown.getMessage());
    }
    
}
