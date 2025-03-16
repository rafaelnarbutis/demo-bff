package com.app.bff.store.domain.services;

public interface QueryService<I,O> {
    O execute(I input);
}
