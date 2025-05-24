package com.fiap.trabalho1.fiap.usecases.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.gateways.OrderRepository;

@Service
public class UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;

    public UpdateOrderStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(UUID orderId, String newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new RuntimeException("Pedido não encontrado: " + orderId);
        }

        List<String> validStatuses = List.of("RECEIVED", "PREPARATION", "READY", "FINISHED");
        if (!validStatuses.contains(newStatus.toUpperCase())) {
            throw new IllegalArgumentException("Status inválido: " + newStatus);
        }

        Order order = optionalOrder.get();
        if(!order.getisPaid() && newStatus.toUpperCase() != "RECEIVED") {
            throw new IllegalArgumentException("Não pode trocar o status se não estiver pago.");
        }
        order.setStatus(newStatus.toUpperCase());
        return orderRepository.save(order);
    }
}
