package com.ra34.projecte2.Service;

import org.springframework.stereotype.Service;

import com.ra34.projecte2.Model.Order;

@Service
public class OrderService {

    @AutoWired OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

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
