package com.app.bff.store.infra.adapters.webservices.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@FeignClient(name = "order-service", url = "${feing.order-service.url}")
public interface OrderWebService {
    record OrderDTO(String id, String client_id, Set<String> items_id, String total) {}

    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    void createOrder(OrderDTO orderDTO);

    @RequestMapping(method = RequestMethod.GET, value = "/orders/{orderId}")
    OrderDTO getOrderById(String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    Set<OrderDTO> getAllOrders();
}
