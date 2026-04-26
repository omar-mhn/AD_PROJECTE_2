package com.ra34.projecte2.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ra34.projecte2.DTO.OrderDTO;
import com.ra34.projecte2.DTO.OrderItemDTO;
import com.ra34.projecte2.DTO.OrderRequest;
import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.OrderItem;
import com.ra34.projecte2.Model.OrderStatus;


@Component
public class OrderMapper {

// DTO to Entity---------------------------------------//
    public Order toEntity(OrderRequest orderRequest){
        if (orderRequest == null) return null;

        return new Order(orderRequest.getOrderDate(), 0.0, OrderStatus.PENDENT, true);        
    }


// Entity to DTO---------------------------------------//
    public OrderDTO toDTO(Order order){
        if (order == null) return null;

        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        
        // Mapear los items del Order a DTOs iterando la lista
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                OrderItemDTO itemDTO = new OrderItemDTO(item.getQuantity(), item.getUnitPrice());
                itemDTOs.add(itemDTO);
            }
        }

        return new OrderDTO(
            order.getId(), 
            order.getCustomer() != null ? order.getCustomer().getId() : null, 
            order.getOrderDate(), 
            order.getTotalAmount(), 
            order.getOrderStatus() != null ? order.getOrderStatus().toString() : null, 
            itemDTOs
        );
    }
}
