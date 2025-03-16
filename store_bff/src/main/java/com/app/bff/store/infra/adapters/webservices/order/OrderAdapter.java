package com.app.bff.store.infra.adapters.webservices.order;

import com.app.bff.store.domain.gateway.OrderGateway;
import com.app.bff.store.domain.models.Customer;
import com.app.bff.store.domain.models.Order;
import com.app.bff.store.domain.models.vo.ItemsId;

import java.util.stream.Collectors;


public class OrderAdapter implements OrderGateway {
    private final OrderWebService orderWebService;

    public OrderAdapter(OrderWebService orderWebService) {
        this.orderWebService = orderWebService;
    }

    @Override
    public void createNewOrder(Order order) {
        orderWebService.createOrder(new OrderWebService.OrderDTO(
                order.getOrderId(),
                order.getCustomerId(),
                order.getItemsId().stream().map(ItemsId::getId).collect(Collectors.toSet()),
                order.getTotal().toString())
        );
    }

    @Override
    public void loadCustomerOrder(Customer costumer, String orderId) {
        final OrderWebService.OrderDTO orderDTO = orderWebService.getOrderById(orderId);

        costumer.addOrder(orderDTO.id(), orderDTO.items_id().stream().map(ItemsId::new).collect(Collectors.toSet()), Double.valueOf(orderDTO.total()));
    }

}
