package com.app.bff.store.infra.configs;

import com.app.bff.store.infra.adapters.webservices.order.OrderAdapter;
import com.app.bff.store.infra.adapters.webservices.order.OrderWebService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServicesConfig {

    @Bean
    public OrderAdapter orderAdapter(OrderWebService orderWebService) {
        return new OrderAdapter(orderWebService);
    }
}
