package com.fiap.trabalho1.fiap.external.request;

import com.fiap.trabalho1.fiap.entities.OrderStatus;

public class UpdateOrderStatusRequest {
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
