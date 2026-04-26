package com.ra34.projecte2.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ra34.projecte2.DTO.OrderDTO;
import com.ra34.projecte2.DTO.OrderItemDTO;
import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.Order_item;

@Component
public class OrderMapper {
    public OrderDTO toDTO(Order order) {
        if (order == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        
        // Convertim l'ENUM a String
        if (order.getOrderStatus() != null) {
            dto.setOrderStatus(order.getOrderStatus().toString());
        }

        //  MAPPER DE LA LLISTA D'ITEMS 
        List<OrderItemDTO> itemDtos = new ArrayList<>();
      
        if (order.getItems() != null) {
            for (Order_item item : order.getItems()) {
                OrderItemDTO iDto = new OrderItemDTO();
                iDto.setId(item.getId());
                iDto.setQuantity(item.getQuantity());
                iDto.setUnitPrice(item.getUnitPrice());
                
                // Obtenim les dades del producte 
                if (item.getProduct() != null) {
                    iDto.setProductId(item.getProduct().getId());
                    iDto.setProductName(item.getProduct().getName());
                }
                
                itemDtos.add(iDto);
            }
        }
        
        dto.setItems(itemDtos);
        return dto;
    }
}
