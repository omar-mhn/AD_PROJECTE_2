package com.ra34.projecte2.Mapper;

import org.springframework.stereotype.Component;

import com.ra34.projecte2.DTO.OrderRequest;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.orderStatus;

@Component
public class OrderMapper {

// to DTO to Entity---------------------------------------//
    public Order toEntity(OrderRequest orderRequest){
        Order order = new Order();
        
        Customer customer = new Customer(orderRequest.getCustomerId());
        order.setCustomer(customer);
        order.setOrderStatus(orderStatus.PENDENT);
        
        return order;
    }
}

/*
public class OrderMapper {

// to DTO to Entity---------------------------------------//
    public Order toEntity(OrderRequest orderRequest){
        Order order = new Order();
        
        Customer customer = new Customer(orderRequest.getCustomerId());
        Order order = new Order(
            order.setCustomer(customer),
            orderRequest.getOrder().getOrderDate(),
            orderRequest.getOrder().getTotalAmount(),
            order.setOrderStatus(orderStatus.PENDENT)
        );


        order.setCustomer(customer);
        order.setOrderStatus(orderStatus.PENDENT);
        
        return order;
    }
}
*/
