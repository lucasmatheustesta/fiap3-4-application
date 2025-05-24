package com.fiap.trabalho1.fiap.usecases.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.entities.OrderStatus;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

@Service
public class UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;

    public UpdateOrderStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(UUID orderId, OrderStatus newStatus) {
        Optional<Order> optionalOrder = this.orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new RuntimeException("Pedido não encontrado: " + orderId);
        }

        List<OrderStatus> validStatuses = List.of(OrderStatus.RECEIVED, OrderStatus.PREPARATION, OrderStatus.READY, OrderStatus.FINISHED);
        if (!validStatuses.contains(newStatus)) {
            throw new IllegalArgumentException("Status inválido: " + newStatus);
        }

        Order order = optionalOrder.get();
        if(!order.getisPaid() && newStatus != OrderStatus.RECEIVED) {
            throw new IllegalArgumentException("Não pode trocar o status se não estiver pago.");
        }
        
        order.setStatus(newStatus);
        return this.orderRepository.save(order);
    }
}
