package com.fiap.trabalho1.fiap.usecases.category;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;

class CreateCateogryUseCaseTest {

    private CategoryRepository categoryRepository;
    private CreateCateogryUseCase createCateogryUseCase;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        createCateogryUseCase = new CreateCateogryUseCase(categoryRepository);
    }

    @Test
    void shouldCreateAndSaveCategory() {
        String name = "Test Category";
        String type = "Test Type";
        
        // Para simular o ID sendo gerado dentro do use case, usamos any(Category.class) no mock
        Category returnedCategory = mock(Category.class);
        when(categoryRepository.save(any(Category.class))).thenReturn(returnedCategory);

        Category result = createCateogryUseCase.execute(name, type);

        assertNotNull(result);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void shouldPropagateExceptionWhenRepositoryFails() {
        String name = "Erro Category";
        String type = "Erro Type";
        when(categoryRepository.save(any(Category.class)))
                .thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class,
                () -> createCateogryUseCase.execute(name, type));
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}
