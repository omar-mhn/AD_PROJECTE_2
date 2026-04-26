package com.ra34.projecte2.Mapper;

import org.springframework.stereotype.Component;

import com.ra34.projecte2.Model.OrderItem;

@Component
public class OrderItemMapper {

    public OrderItem orderItem(OrderItemRequest request){
        return new OrderItem(request.getQuantity(), request.getUnitPrice());
    }
}
