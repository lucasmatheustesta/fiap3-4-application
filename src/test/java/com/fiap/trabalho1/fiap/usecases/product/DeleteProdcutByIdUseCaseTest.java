package com.fiap.trabalho1.fiap.usecases.product;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.trabalho1.fiap.gateways.ProductRepository;

class DeleteProdcutByIdUseCaseTest {

    private ProductRepository productRepository;
    private DeleteProdcutByIdUseCase deleteProdcutByIdUseCase;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        deleteProdcutByIdUseCase = new DeleteProdcutByIdUseCase(productRepository);
    }

    @Test
    void shouldCallDeleteByIdOnRepository() {
        UUID productId = UUID.randomUUID();

        deleteProdcutByIdUseCase.execute(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void shouldPropagateExceptionIfRepositoryThrows() {
        UUID productId = UUID.randomUUID();
        doThrow(new RuntimeException("Error")).when(productRepository).deleteById(productId);

        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> deleteProdcutByIdUseCase.execute(productId)
        );

        verify(productRepository, times(1)).deleteById(productId);
    }
}
