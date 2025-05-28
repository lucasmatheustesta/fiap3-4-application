package com.fiap.trabalho1.fiap.usecases.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.fiap.trabalho1.fiap.entities.Category;
import com.fiap.trabalho1.fiap.gateways.CategoryRepository;

class ListAllCategoriesUseCaseTest {

    private CategoryRepository categoryRepository;
    private ListAllCategoriesUseCase listAllCategoriesUseCase;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        listAllCategoriesUseCase = new ListAllCategoriesUseCase(categoryRepository);
    }

    @Test
    void shouldCallRepositoryWithCorrectPageRequest() {
        int page = 0;
        int size = 5;
        Page<Category> fakePage = new PageImpl<>(Collections.emptyList());
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(fakePage);

        Page<Category> result = listAllCategoriesUseCase.listAllCategories(page, size);

        assertEquals(fakePage, result);
        ArgumentCaptor<PageRequest> captor = ArgumentCaptor.forClass(PageRequest.class);
        verify(categoryRepository).findAll(captor.capture());
        
        PageRequest capturedRequest = captor.getValue();
        
        assertEquals(page, capturedRequest.getPageNumber());
        assertEquals(size, capturedRequest.getPageSize());
        assertEquals(Sort.Direction.ASC, capturedRequest.getSort().getOrderFor("name").getDirection());
    }

    @Test
    void shouldReturnPageWithCategories() {
        int page = 1;
        int size = 2;
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = List.of(category1, category2);
        Page<Category> expectedPage = new PageImpl<>(categories, PageRequest.of(page, size, Sort.by("name")), 2);
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        Page<Category> result = listAllCategoriesUseCase.listAllCategories(page, size);

        assertEquals(2, result.getNumberOfElements());
        assertEquals(categories, result.getContent());
    }

    @Test
    void shouldReturnEmptyPageIfNoneFound() {
        when(categoryRepository.findAll(any(PageRequest.class)))
                .thenReturn(Page.empty());

        Page<Category> result = listAllCategoriesUseCase.listAllCategories(0, 10);


        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldPropagateExceptionIfRepositoryFails() {
        when(categoryRepository.findAll(any(PageRequest.class)))
                .thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> listAllCategoriesUseCase.listAllCategories(0, 10));
        verify(categoryRepository, times(1)).findAll(any(PageRequest.class));
    }
    
}
