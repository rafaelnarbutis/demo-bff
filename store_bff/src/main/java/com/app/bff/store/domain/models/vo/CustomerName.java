package com.app.bff.store.domain.models.vo;

public class CustomerName {
    private String name;

    public CustomerName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
