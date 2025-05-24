package com.fiap.trabalho1.fiap.usecases.order;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListSortedOrdersUseCase {

    private final OrderRepository orderRepository;

    public ListSortedOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<Order> execute(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findActiveOrdersOrdered(pageable);
    }
}
