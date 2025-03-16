package com.app.bff.store.domain.models.vo;

public class ItemsId {
    private String id;

    public ItemsId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
