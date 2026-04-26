package com.ra34.projecte2.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.ra34.projecte2.DTO.OrderRequest;
import com.ra34.projecte2.Mapper.OrderMapper;
import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Repository.CustomerRepository;
import com.ra34.projecte2.Repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;    
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CustomerRepository customerRepository;

    public OrderService (OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper, CustomerRepository customerRepository){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.customerRepository = customerRepository;   
    }

    public OrderDTO createOrder(OrderRequest request) {


        Order order = orderMapper.toEntity(request);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No s'ha trobat la comanda amb id: " + id));
        return orderMapper.toDTO(order);
    }

}
