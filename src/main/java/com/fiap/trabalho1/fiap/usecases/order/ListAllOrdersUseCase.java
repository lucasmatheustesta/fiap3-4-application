package com.fiap.trabalho1.fiap.usecases.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

@Service
public class ListAllOrdersUseCase {

    private final OrderRepository orderRepository;

    public ListAllOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public Page<Order> listOrders(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "orderDate");
		return this.orderRepository.findAll(pageRequest);
	}
    
}
