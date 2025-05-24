package com.fiap.trabalho1.fiap.application.services;

import com.fiap.trabalho1.fiap.domain.OrderModel;
import com.fiap.trabalho1.fiap.utils.EStatus;

public class OrderService {
    public OrderModel sendToTheChicken(OrderModel orderModel) {
        orderModel.setStatus(EStatus.RECEIVED.toString());
        // Lógica de enviar para a fila
        return  orderModel;
    }

    public OrderModel finishOrder(OrderModel orderModel) {
        orderModel.setStatus(EStatus.FINISHED.toString());
        // Lógica para finalizar o pedido (fila)
        return orderModel;
    }

}
