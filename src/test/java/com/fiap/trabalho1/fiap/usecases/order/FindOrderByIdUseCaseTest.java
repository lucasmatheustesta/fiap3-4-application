package com.fiap.trabalho1.fiap.usecases.order;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindOrderByIdUseCaseTest {

    private OrderRepository orderRepository;
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        findOrderByIdUseCase = new FindOrderByIdUseCase(orderRepository);
    }

    @Test
    void shouldReturnOrderWhenFound() {
        UUID orderId = UUID.randomUUID();
        Order mockOrder = mock(Order.class);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        Optional<Order> result = findOrderByIdUseCase.execute(orderId);

        assertTrue(result.isPresent());
        assertEquals(mockOrder, result.get());
        verify(orderRepository).findById(orderId);
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFound() {
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> result = findOrderByIdUseCase.execute(orderId);

        assertFalse(result.isPresent());
        verify(orderRepository).findById(orderId);
    }

}
