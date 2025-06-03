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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

class ListAllOrdersUseCaseTest {

    private OrderRepository orderRepository;
    private ListAllOrdersUseCase listAllOrdersUseCase;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        listAllOrdersUseCase = new ListAllOrdersUseCase(orderRepository);
    }

    @Test
    void shouldReturnOrdersWhenPageIsValid() {
        List<Order> orders = List.of(mock(Order.class), mock(Order.class));
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(orders);
        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);


        Page<Order> result = listAllOrdersUseCase.listOrders(0, 2);

        
        ArgumentCaptor<PageRequest> pageRequestCaptor = ArgumentCaptor.forClass(PageRequest.class);
        verify(orderRepository).findAll(pageRequestCaptor.capture());

        
        PageRequest pageRequest = pageRequestCaptor.getValue();
        assertEquals(0, pageRequest.getPageNumber());
        assertEquals(2, pageRequest.getPageSize());
        assertEquals(Sort.Direction.ASC, pageRequest.getSort().getOrderFor("orderDate").getDirection());

        
        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(orders.size(), result.getContent().size());
    }

    @Test
    void shouldReturnEmptyPageWhenNoOrdersFound() {
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(List.of());
        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);

        
        Page<Order> result = listAllOrdersUseCase.listOrders(0, 2);

        
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void shouldReturnEmptyPageWhenPageIsOutOfBounds() {
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(List.of());
        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);

        Page<Order> result = listAllOrdersUseCase.listOrders(5, 2);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenPageSizeIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            listAllOrdersUseCase.listOrders(0, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            listAllOrdersUseCase.listOrders(0, -2);
        });
    }

    @Test
    void shouldReturnOrdersWithCorrectSorting() {
        List<Order> orders = List.of(mock(Order.class), mock(Order.class));
        Page<Order> mockPage = Mockito.mock(Page.class);
        when(mockPage.getContent()).thenReturn(orders);
        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);

        Page<Order> result = listAllOrdersUseCase.listOrders(0, 2);

        ArgumentCaptor<PageRequest> pageRequestCaptor = ArgumentCaptor.forClass(PageRequest.class);
        verify(orderRepository).findAll(pageRequestCaptor.capture());
        PageRequest pageRequest = pageRequestCaptor.getValue();

        assertEquals(Sort.Direction.ASC, pageRequest.getSort().getOrderFor("orderDate").getDirection());
    }
    
}
