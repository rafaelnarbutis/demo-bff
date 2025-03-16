package com.app.bff.store.domain.gateway;

import com.app.bff.store.domain.models.Customer;
import com.app.bff.store.domain.models.Order;

public interface OrderGateway {
    void createNewOrder(Order order);

    void loadCustomerOrder(Customer customer, String orderId);
}
