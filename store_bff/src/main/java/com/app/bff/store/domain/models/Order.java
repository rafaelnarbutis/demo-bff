package com.app.bff.store.domain.models;

import com.app.bff.store.domain.models.vo.CustomerId;
import com.app.bff.store.domain.models.vo.ItemsId;
import com.app.bff.store.domain.models.vo.OrderId;

import java.util.Set;

public class Order {
    private OrderId id;
    private CustomerId customerId;
    private Set<ItemsId> itemsId;
    private Double total;

    private Order(OrderId id, CustomerId customerId, Set<ItemsId> itemsId, Double total) {
        this.id = id;
        this.customerId = customerId;
        this.itemsId = itemsId;
        this.total = total;
    }

    private Order() {}

    protected static Order CreateNewOrder(CustomerId customerId, Set<ItemsId> itemsId, Double total) {
        return new Order(OrderId.CreateOrderId(), customerId, itemsId, total);
    }

    protected static Order RestoreOrder(String id, CustomerId customerId, Set<ItemsId> itemsId, Double total) {
        return new Order(new OrderId(id), customerId, itemsId, total);
    }

    public String getOrderId() {
        return id.getId();
    }

    public String getCustomerId() {
        return customerId.getId();
    }

    public Set<ItemsId> getItemsId() {
        return itemsId;
    }

    public Double getTotal() {
        return total;
    }
}
