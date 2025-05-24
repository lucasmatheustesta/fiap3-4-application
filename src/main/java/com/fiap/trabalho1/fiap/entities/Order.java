package com.fiap.trabalho1.fiap.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders") 
public class Order {
    
	@Id
    private UUID id;
    
	private UUID clientId;
    
    private List<UUID> productIds;
    
    private LocalDate orderDate;
    
    private Double orderTotal;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    private boolean isPaid;

    public Order() {}

    public Order(UUID idOrder, UUID clientId, List<UUID> productIds, LocalDate orderDate, Double orderTotal, OrderStatus status) {
        this.id = idOrder;
        this.clientId = clientId;
        this.productIds = productIds;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.status = status;
        this.isPaid = false;
    }

    // Regras de negócio
    public void approve() {
        this.isPaid = true;
        this.status = OrderStatus.PREPARATION;
    }

    public void cancel() {
        if (isPaid) {
            throw new IllegalStateException("Approved orders cannot be canceled.");
        }
    }

    // Getters e Setters
    public UUID getIdOrder() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public List<UUID> getProductIds() {
        return productIds;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean getisPaid() {
        return this.isPaid;
    }

}
