package com.fiap.trabalho1.fiap.usecases.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;
import com.fiap.trabalho1.fiap.infrastructure.exceptions.BusinessValidationException;

class CreateOrderUseCaseTest {

    private OrderRepository orderRepository;
    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        createOrderUseCase = new CreateOrderUseCase(orderRepository);
    }

    @Test
    void shouldCreateAndSaveOrderSuccessfully() {
        UUID clientId = UUID.randomUUID();
        List<UUID> productIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        Double orderTotal = 200.0;

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = createOrderUseCase.execute(clientId, productIds, orderTotal);

        assertNotNull(order);
        assertEquals(clientId, order.getClientId());
        assertEquals(productIds, order.getProductIds());
        assertEquals(orderTotal, order.getOrderTotal());
        assertEquals("RECEIVED", order.getStatus());
        assertEquals(LocalDate.now(), order.getOrderDate());
        assertNotNull(order.getIdOrder());
    }
    
    @Test
    void shouldNotCreateAndSaveOrderWithoutId() {
        List<UUID> productIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        Double orderTotal = 200.0;

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = createOrderUseCase.execute(null, productIds, orderTotal);

        assertNotNull(order.getIdOrder());
    }

    @Test
    void shouldThrowExceptionWhenOrderTotalIsInvalid() {
        UUID clientId = UUID.randomUUID();
        List<UUID> productIds = List.of(UUID.randomUUID());

        double invalidTotal = -90.0;

        BusinessValidationException thrown = assertThrows(
                BusinessValidationException.class,
                () -> createOrderUseCase.execute(clientId, productIds, invalidTotal)
        );

        assertEquals("Order total must be positive value", thrown.getMessage());
        verify(orderRepository, never()).save(any());
    }
}
