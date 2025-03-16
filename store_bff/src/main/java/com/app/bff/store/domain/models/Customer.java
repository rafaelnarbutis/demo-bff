package com.app.bff.store.domain.models;

import com.app.bff.store.domain.models.vo.*;

import java.util.HashSet;
import java.util.Set;

public class Customer {
    private CustomerId customerId;
    private CustomerName customerName;
    private CustomerEmail customerEmail;
    private String phone;
    private Set<Order> orders;

    public Customer(CustomerId id, CustomerName customerName, CustomerEmail customerEmail, String phone, Set<Order> orders) {
        this.customerId = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.phone = phone;
        this.orders = orders;
    }

    private Customer() {}

    public static Customer CreateNewCustomer(String name, String email, String phone) {
        return new Customer(CustomerId.CreateCustomerId(), new CustomerName(name), new CustomerEmail(email), phone, new HashSet<>());
    }

    public String getCostumerName() {
        return customerName.getName();
    }

    public String getCostumerEmail() {
        return customerEmail.getEmail();
    }

    public String getPhone() {
        return phone;
    }

    public String getCustomerId() {
        return customerId.getId();
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Order addNewOrder(Set<ItemsId> itemsId, Double total) {
        final Order order = Order.CreateNewOrder(customerId, itemsId, total);
        this.orders.add(order);
        return order;
    }

    public void addOrder(String orderId, Set<ItemsId> itemsId, Double total) {
        final Order order = Order.RestoreOrder(orderId, customerId, itemsId, total);
        this.orders.add(order);
    }

    public Order findOrder(String orderId) {
        return orders.stream().filter(order -> order.getOrderId().equals(orderId)).findFirst().orElse(null);
    }
}
