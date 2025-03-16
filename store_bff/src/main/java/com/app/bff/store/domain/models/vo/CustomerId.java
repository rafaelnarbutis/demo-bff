package com.app.bff.store.domain.models.vo;

import java.util.UUID;

public class CustomerId {
    private String id;

    public CustomerId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.id = id;
    }

    public static CustomerId CreateCustomerId() {
        return new CustomerId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }
}
