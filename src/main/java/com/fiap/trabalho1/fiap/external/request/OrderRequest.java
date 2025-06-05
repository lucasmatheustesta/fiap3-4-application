package com.fiap.trabalho1.fiap.external.request;

import java.util.List;
import java.util.UUID;

public class OrderRequest {

    private UUID clientId;
    private List<UUID> productIds;
    private Double orderTotal;

    public UUID getClientId() {
        return clientId;
    }

    public List<UUID> getProductIds() {
        return productIds;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

}
