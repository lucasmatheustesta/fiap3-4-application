package com.fiap.trabalho1.fiap.entities;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Table(name = "orders") 
public class Order {
    @Id
    private UUID id;
    private UUID clientId;
    private List<UUID> productIds;
    private LocalDate orderDate;
    private Double orderTotal;
    private String status;
    private boolean isPaid;

    public Order() {}

    public Order(UUID idOrder, UUID clientId, List<UUID> productIds, LocalDate orderDate, Double orderTotal, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getisPaid() {
        return this.isPaid;
    }

    
}