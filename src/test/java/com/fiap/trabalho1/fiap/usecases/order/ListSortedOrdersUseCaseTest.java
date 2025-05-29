package com.fiap.trabalho1.fiap.usecases.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

class ListSortedOrdersUseCaseTest {

    private OrderRepository orderRepository;
    private ListSortedOrdersUseCase listSortedOrdersUseCase;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        listSortedOrdersUseCase = new ListSortedOrdersUseCase(orderRepository);
    }

    @Test
    void shouldReturnOrdersWhenPageIsValid() {
        List<Order> orders = List.of(mock(Order.class), mock(Order.class));
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(orders);
        when(orderRepository.findActiveOrdersOrdered(any(Pageable.class))).thenReturn(mockPage);

        Page<Order> result = listSortedOrdersUseCase.execute(0, 2);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(orderRepository).findActiveOrdersOrdered(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertEquals(0, pageable.getPageNumber());
        assertEquals(2, pageable.getPageSize());


        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(orders.size(), result.getContent().size());
    }

    @Test
    void shouldReturnEmptyPageWhenNoOrdersFound() {
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(List.of());
        when(orderRepository.findActiveOrdersOrdered(any(Pageable.class))).thenReturn(mockPage);


        Page<Order> result = listSortedOrdersUseCase.execute(0, 2);


        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void shouldReturnEmptyPageWhenPageIsOutOfBounds() {
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(List.of());
        when(orderRepository.findActiveOrdersOrdered(any(Pageable.class))).thenReturn(mockPage);

        Page<Order> result = listSortedOrdersUseCase.execute(5, 2);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenPageSizeIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            listSortedOrdersUseCase.execute(0, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            listSortedOrdersUseCase.execute(0, -2);
        });
    }

    @Test
    void shouldReturnOrdersWithCorrectSorting() {
        List<Order> orders = List.of(mock(Order.class), mock(Order.class));
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(orders);
        when(orderRepository.findActiveOrdersOrdered(any(Pageable.class))).thenReturn(mockPage);

        Page<Order> result = listSortedOrdersUseCase.execute(0, 2);


        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(orderRepository).findActiveOrdersOrdered(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertNotNull(pageable.getSort());
        assertEquals(0, pageable.getPageNumber());
        assertEquals(2, pageable.getPageSize());
    }

    @Test
    void shouldCallRepositoryWithCorrectPageable() {
        List<Order> orders = List.of(mock(Order.class), mock(Order.class));
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(orders);
        when(orderRepository.findActiveOrdersOrdered(any(Pageable.class))).thenReturn(mockPage);

        Page<Order> result = listSortedOrdersUseCase.execute(1, 5);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(orderRepository).findActiveOrdersOrdered(pageableCaptor.capture());
        
        Pageable pageable = pageableCaptor.getValue();
        assertEquals(1, pageable.getPageNumber());
        assertEquals(5, pageable.getPageSize());
    }
    
}
