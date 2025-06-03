package com.fiap.trabalho1.fiap.usecases.order;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;
import com.fiap.trabalho1.fiap.infrastructure.exceptions.BusinessValidationException;


@Service
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;

    public CreateOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(UUID clientId, List<UUID> productIds, Double orderTotal) {
    	if (orderTotal <= 0.0) {
    		throw new BusinessValidationException("Order total must be positive value");
    	}
    	
        Order order = new Order(
                UUID.randomUUID(),
                clientId,
                productIds,
                LocalDate.now(),
                orderTotal,
                "RECEIVED"
        );
        
        return orderRepository.save(order);
    }
    
}