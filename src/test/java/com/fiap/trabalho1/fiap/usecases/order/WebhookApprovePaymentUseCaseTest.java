package com.fiap.trabalho1.fiap.usecases.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

class WebhookApprovePaymentUseCaseTest {

    private OrderRepository orderRepository;
    private WebhookApprovePaymentUseCase webhookApprovePaymentUseCase;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        webhookApprovePaymentUseCase = new WebhookApprovePaymentUseCase(orderRepository);
    }

    @Test
    void shouldApprovePaymentWhenOrderFound() {
        UUID orderId = UUID.randomUUID();
        Order mockOrder = mock(Order.class);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        Order approvedOrder = webhookApprovePaymentUseCase.approveOrder(orderId);

        verify(mockOrder).approve();

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        assertSame(mockOrder, orderCaptor.getValue());


        assertNotNull(approvedOrder);
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        UUID orderId = UUID.randomUUID();


        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            webhookApprovePaymentUseCase.approveOrder(orderId);
        });


        assertEquals("Pedido não encontrado: " + orderId, exception.getMessage());
    }

    @Test
    void shouldSaveOrderAfterApproval() {
        UUID orderId = UUID.randomUUID();
        Order mockOrder = mock(Order.class);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        Order result = webhookApprovePaymentUseCase.approveOrder(orderId);

        verify(mockOrder).approve();

        verify(orderRepository).save(mockOrder);

        assertSame(mockOrder, result);
    }
    
}
