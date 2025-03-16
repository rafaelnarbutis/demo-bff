package com.app.bff.store.domain.services.order;

import com.app.bff.store.domain.gateway.OrderGateway;
import com.app.bff.store.domain.models.Customer;
import com.app.bff.store.domain.models.Order;
import com.app.bff.store.domain.models.vo.ItemsId;
import com.app.bff.store.domain.services.CommandService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateNewOrderService implements CommandService<CreateNewOrderService.CreateNewOrderCommand> {
    private final OrderGateway orderGateway;

    public CreateNewOrderService(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public void execute(CreateNewOrderCommand createNewOrderCommand) {

        System.out.println("CreateNewOrderService.execute");

        final Order order =  Customer.CreateNewCustomer("name", "email", "phone").addNewOrder(createNewOrderCommand.itemsId.stream()
                .map(ItemsId::new)
                .collect(Collectors.toSet()), 10.0);

        orderGateway.createNewOrder(order);
    }

    public record CreateNewOrderCommand(String customerId, List<String> itemsId) {}
}
