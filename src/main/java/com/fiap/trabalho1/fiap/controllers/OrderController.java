package com.fiap.trabalho1.fiap.controllers;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.trabalho1.fiap.entities.Order;
import com.fiap.trabalho1.fiap.external.request.OrderRequest;
import com.fiap.trabalho1.fiap.external.request.UpdateOrderStatusRequest;
import com.fiap.trabalho1.fiap.usecases.order.CreateOrderUseCase;
import com.fiap.trabalho1.fiap.usecases.order.ListAllOrdersUseCase;
import com.fiap.trabalho1.fiap.usecases.order.ListSortedOrdersUseCase;
import com.fiap.trabalho1.fiap.usecases.order.UpdateOrderStatusUseCase;
import com.fiap.trabalho1.fiap.usecases.order.WebhookApprovePaymentUseCase;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
	private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final ListAllOrdersUseCase listAllOrdersUseCase;
    private final WebhookApprovePaymentUseCase webhookApprovePaymentUseCase;
    private final ListSortedOrdersUseCase listSortedOrdersUseCase;


    public OrderController(CreateOrderUseCase createOrderUseCase,
                       ListAllOrdersUseCase listAllOrdersUseCase,
                       WebhookApprovePaymentUseCase webhookApprovePaymentUseCase,
                       UpdateOrderStatusUseCase updateOrderStatusUseCase,
                       ListSortedOrdersUseCase listSortedOrdersUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.listAllOrdersUseCase = listAllOrdersUseCase;
        this.webhookApprovePaymentUseCase = webhookApprovePaymentUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.listSortedOrdersUseCase = listSortedOrdersUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        Set<String> allowedStatuses = Set.of("RECEIVED", "PREPARATION", "READY", "FINISHED");

        if (!allowedStatuses.contains(request.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only are allowed: RECEIVED, PREPARATION, READY and FINISHED");
        }

        Order order = createOrderUseCase.execute(
                request.getClientId(),
                request.getProductIds(),
                request.getOrderTotal()
        );
        return ResponseEntity.ok(order);
    }

    @PutMapping("/orders/{id}/approve")
    public ResponseEntity<?> approveOrder(@PathVariable UUID id) {
        try {
            Order order = webhookApprovePaymentUseCase.approveOrder(id);
            return ResponseEntity.ok(order);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping
	public ResponseEntity<Page<Order>> listOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
												  @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
    	Page<Order> products = this.listAllOrdersUseCase.listOrders(page, size);
    	if (products.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	return ResponseEntity.ok(products);
	}

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable UUID id,
                                            @RequestBody UpdateOrderStatusRequest request) {
        try {
            Set<String> allowedStatuses = Set.of("RECEIVED", "PREPARATION", "READY", "FINISHED");

            if (!allowedStatuses.contains(request.getStatus())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Only are allowed: RECEIVED, PREPARATION, READY and FINISHED");
            }
            Order order = updateOrderStatusUseCase.execute(id, request.getStatus());
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Lista pedidos", description = "A lista de pedidos deverá retorná-los com suas descrições, ordenados com a seguinte regra: 1. Pronto > Em Preparação > Recebido; 2. Pedidos mais antigos primeiro e mais novos depois; 3. Pedidos com status Finalizado não devem aparecer na lista")
    @GetMapping("/active")
    public ResponseEntity<Page<Order>> listSortedOrders(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        Page<Order> orders = listSortedOrdersUseCase.execute(page, size);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

}
