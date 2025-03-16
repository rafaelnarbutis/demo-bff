package com.app.bff.store.infra.adapters.entrypoints.graphqls;

import com.app.bff.store.domain.services.CommandService;
import com.app.bff.store.domain.services.order.CreateNewOrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderGraphql {

    private final CommandService<CreateNewOrderService.CreateNewOrderCommand> createNewOrderService;

    public OrderGraphql(CommandService createNewOrderService) {
        this.createNewOrderService = createNewOrderService;
    }

    @MutationMapping
    public Boolean createOrder(@Argument String customerId, @Argument List<String> itemsId) {
        createNewOrderService.execute(new CreateNewOrderService.CreateNewOrderCommand(customerId, itemsId));

        return true;
    }
}
