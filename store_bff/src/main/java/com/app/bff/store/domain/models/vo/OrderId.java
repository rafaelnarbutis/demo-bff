package com.app.bff.store.domain.models.vo;

import java.util.UUID;

public class OrderId {
    private String id;

    public OrderId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        this.id = id;
    }

    public static OrderId CreateOrderId() {
        return new OrderId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }
}
