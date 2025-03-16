package com.app.bff.store.infra.configs;

import com.app.bff.store.domain.services.order.CreateNewOrderService;
import com.app.bff.store.infra.adapters.webservices.order.OrderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

    @Bean
    public CreateNewOrderService createNewOrderService(OrderAdapter orderAdapter) {
        return new CreateNewOrderService(orderAdapter);
    }
}
