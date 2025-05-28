package com.fiap.trabalho1.fiap.usecases.category;

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

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;

class FindCategoryByIdUseCaseTest {

    private CategoryRepository categoryRepository;
    private FindCategoryByIdUseCase findCategoryByIdUseCase;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        findCategoryByIdUseCase = new FindCategoryByIdUseCase(categoryRepository);
    }

    @Test
    void shouldReturnCategoryWhenExists() {
        UUID categoryId = UUID.randomUUID();
        Category category = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<Category> result = findCategoryByIdUseCase.execute(categoryId);

        assertTrue(result.isPresent());
        assertEquals(category, result.get());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void shouldReturnEmptyWhenCategoryDoesNotExist() {
        UUID categoryId = UUID.randomUUID();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());


        Optional<Category> result = findCategoryByIdUseCase.execute(categoryId);


        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void shouldPropagateExceptionIfRepositoryFails() {
        UUID categoryId = UUID.randomUUID();
        when(categoryRepository.findById(categoryId)).thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> findCategoryByIdUseCase.execute(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
    }
    
}
