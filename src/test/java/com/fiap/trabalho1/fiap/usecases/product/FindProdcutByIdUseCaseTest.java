package com.fiap.trabalho1.fiap.usecases.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;

class FindProdcutByIdUseCaseTest {

    private ProductRepository productRepository;
    private FindProdcutByIdUseCase findProdcutByIdUseCase;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        findProdcutByIdUseCase = new FindProdcutByIdUseCase(productRepository);
    }

    @Test
    void shouldReturnProductIfExists() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        Optional<Product> optionalProduct = Optional.of(product);
        when(productRepository.findById(id)).thenReturn(optionalProduct);

        Optional<Product> result = findProdcutByIdUseCase.execute(id);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnEmptyIfProductNotExists() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = findProdcutByIdUseCase.execute(id);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void shouldPropagateExceptionIfRepositoryFails() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> findProdcutByIdUseCase.execute(id));
        verify(productRepository, times(1)).findById(id);
    }
}
