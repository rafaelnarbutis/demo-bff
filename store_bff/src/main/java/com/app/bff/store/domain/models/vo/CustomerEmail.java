package com.app.bff.store.domain.models.vo;

public class CustomerEmail {
    private String email;

    public CustomerEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
