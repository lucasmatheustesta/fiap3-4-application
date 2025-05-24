package com.fiap.trabalho1.fiap.usecases.order;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

@Service
public class FindOrderByIdUseCase {

	private final OrderRepository orderRepository;

    public FindOrderByIdUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public Optional<Order> execute(UUID orderId) {
    	return this.orderRepository.findById(orderId);
    }
    
}
