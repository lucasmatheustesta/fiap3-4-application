package com.fiap.trabalho1.fiap.usecases.product;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fiap.trabalho1.fiap.entities.Product;
import com.fiap.trabalho1.fiap.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

class ListAllProductsUseCaseTest {

    private ProductRepository productRepository;
    private ListAllProductsUseCase listAllProductsUseCase;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        listAllProductsUseCase = new ListAllProductsUseCase(productRepository);
    }

    @Test
    void shouldCallRepositoryWithCorrectPageRequest() {
        int page = 1;
        int size = 10;
        Page<Product> productsPage = new PageImpl<>(List.of());
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(productsPage);

        Page<Product> result = listAllProductsUseCase.listAllProdcuts(page, size);

        assertEquals(productsPage, result);

        ArgumentCaptor<PageRequest> captor = ArgumentCaptor.forClass(PageRequest.class);
        verify(productRepository).findAll(captor.capture());
        PageRequest captured = captor.getValue();
        assertEquals(page, captured.getPageNumber());
        assertEquals(size, captured.getPageSize());
        assertEquals(Sort.Direction.ASC, captured.getSort().getOrderFor("name").getDirection());
        assertEquals("name", captured.getSort().iterator().next().getProperty());
    }

    @Test
    void shouldReturnEmptyPageWhenNoProductsFound() {
        int page = 0;
        int size = 5;
        Page<Product> emptyPage = new PageImpl<>(List.of());
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        Page<Product> result = listAllProductsUseCase.listAllProdcuts(page, size);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnProductsPageWhenFound() {
        int page = 0;
        int size = 2;
        Product p1 = new Product();
        Product p2 = new Product();
        List<Product> products = Arrays.asList(p1, p2);
        Page<Product> expectedPage = new PageImpl<>(products, PageRequest.of(page, size, Sort.by("name").ascending()), 2);
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        Page<Product> result = listAllProductsUseCase.listAllProdcuts(page, size);

        assertEquals(2, result.getTotalElements());
        assertEquals(products, result.getContent());
    }

    @Test
    void shouldThrowExceptionIfRepositoryThrows() {
        when(productRepository.findAll(any(PageRequest.class))).thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> 
            listAllProductsUseCase.listAllProdcuts(0, 10)
        );
    }
    
}
