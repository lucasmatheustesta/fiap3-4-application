package com.fiap.trabalho1.fiap.usecases.category;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.trabalho1.fiap.gateways.CategoryRepository;

class DeleteCateogryByIdUseCaseTest {

    private CategoryRepository categoryRepository;
    private DeleteCateogryByIdUseCase deleteCateogryByIdUseCase;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        deleteCateogryByIdUseCase = new DeleteCateogryByIdUseCase(categoryRepository);
    }

    @Test
    void shouldCallDeleteByIdOnRepository() {
        // Arrange
        UUID categoryId = UUID.randomUUID();

        // Act
        deleteCateogryByIdUseCase.execute(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void shouldPropagateExceptionIfRepositoryFails() {
        // Arrange
        UUID categoryId = UUID.randomUUID();
        doThrow(new RuntimeException("DB Error")).when(categoryRepository).deleteById(categoryId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> deleteCateogryByIdUseCase.execute(categoryId));
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}
