package com.fiap.trabalho1.fiap.usecases.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.UUID;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;


import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

import org.springframework.stereotype.Service;

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