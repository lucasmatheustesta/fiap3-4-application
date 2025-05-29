package com.fiap.trabalho1.fiap.usecases.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

class UpdateOrderStatusUseCaseTest {

    private OrderRepository orderRepository;
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;
    
    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        
        updateOrderStatusUseCase = new UpdateOrderStatusUseCase(orderRepository);
    }


    @Test
    void shouldThrowExceptionWhenStatusIsInvalid() {
        UUID orderId = UUID.randomUUID();
        Order mockOrder = mock(Order.class);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateOrderStatusUseCase.execute(orderId, "INVALID_STATUS");
        });

        assertEquals("Status inválido: INVALID_STATUS", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotPaidAndStatusNotReceived() {
        UUID orderId = UUID.randomUUID();
        Order mockOrder = mock(Order.class);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(mockOrder.getisPaid()).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateOrderStatusUseCase.execute(orderId, "PREPARATION");
        });

        assertEquals("Não pode trocar o status se não estiver pago.", exception.getMessage());
    }
    
}
