package com.app.bff.store.domain.services;

public interface CommandService<I> {
    void execute(I input);
}
