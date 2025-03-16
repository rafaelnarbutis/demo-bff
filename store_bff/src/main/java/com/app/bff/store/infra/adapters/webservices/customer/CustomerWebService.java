package com.app.bff.store.infra.adapters.webservices.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("customer-service")
public interface CustomerWebService {

    record Customer(String id, String name, String email) {}

    @RequestMapping(method = RequestMethod.GET, value = "/customers/{client_id}", consumes = "application/json")
    Customer getCustomerById(@PathVariable("storeId") String customerId);
}
