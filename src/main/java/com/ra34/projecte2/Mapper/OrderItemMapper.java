package com.ra34.projecte2.Mapper;

import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItem toOrderItem(OrderItemRequestDTO request){
        return new OrderItem(request.getQuantity(), request.getUnitPrice());
    }
}
