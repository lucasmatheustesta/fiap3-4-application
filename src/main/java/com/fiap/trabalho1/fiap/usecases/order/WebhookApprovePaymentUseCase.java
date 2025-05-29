package com.fiap.trabalho1.fiap.usecases.order;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WebhookApprovePaymentUseCase {

    private final OrderRepository orderRepository;

    public WebhookApprovePaymentUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
     public Order approveOrder(UUID orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new EntityNotFoundException("Pedido não encontrado: " + orderId);
        }
        

        Order order = optionalOrder.get();
        order.approve();
        
        return orderRepository.save(order);
    }
    
}