package com.fiap.trabalho1.fiap.usecases.product;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;
import com.fiap.trabalho1.fiap.infrastructure.exceptions.BusinessValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductUseCaseTest {

    private ProductRepository productRepository;
    private CreateProductUseCase createProductUseCase;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        createProductUseCase = new CreateProductUseCase(productRepository);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        String name = "Notebook";
        BigDecimal value = new BigDecimal("2500.00");
        String description = "Notebook Dell 16GB RAM";
        UUID categoryId = UUID.randomUUID();

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product product = createProductUseCase.execute(name, value, description, categoryId);

        assertNotNull(product);
        assertEquals(name, product.getName());
        assertEquals(value, product.getValue());
        assertEquals(description, product.getDescription());
        assertEquals(categoryId, product.getCategoryId());
        assertNotNull(product.getIdProduct());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenValueIsZero() {
        BigDecimal value = BigDecimal.ZERO;

        BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> createProductUseCase.execute("Item", value, "Desc", UUID.randomUUID())
        );

        verify(productRepository, never()).save(any());
        assertEquals("Prodcut value must be a positive value", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        BigDecimal value = new BigDecimal("-5.00");

        BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> createProductUseCase.execute("Item", value, "Desc", UUID.randomUUID())
        );

        verify(productRepository, never()).save(any());
        assertEquals("Prodcut value must be a positive value", thrown.getMessage());
    }
    
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
    	BigDecimal value = new BigDecimal("5.00");
    	BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> createProductUseCase.execute("", value, "Desc", UUID.randomUUID())
        );

        verify(productRepository, never()).save(any());
        assertEquals("Prodcut name must not be null or empty", thrown.getMessage());
    }
    
    @Test
    void shouldThrowExceptionWhenNameIsNull() {
    	BigDecimal value = new BigDecimal("5.00");
    	BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> createProductUseCase.execute(null, value, "Desc", UUID.randomUUID())
        );

        verify(productRepository, never()).save(any());
        assertEquals("Prodcut name must not be null or empty", thrown.getMessage());
    }
    
    @Test
    void shouldThrowExceptionWhenCategoryIdIsNull() {
    	BigDecimal value = new BigDecimal("2500.00");
    	BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> createProductUseCase.execute("Macbook Pro", value, "Desc", null)
        );

        verify(productRepository, never()).save(any());
        assertEquals("Cateogy id must not be null", thrown.getMessage());
    }
}
