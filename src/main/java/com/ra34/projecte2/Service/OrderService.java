package com.ra34.projecte2.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ra34.projecte2.DTO.OrderDTO;
import com.ra34.projecte2.Mapper.OrderMapper;
import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.Order_item;
import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Model.orderStatus;
import com.ra34.projecte2.Repository.OrderRepository;
import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMapper orderMapper;


    @Transactional
    public OrderDTO addProductsToOrder(Long orderId, List<Long> productIds) {
        // Verificar si existe la orden
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        // Por cada ID recibido, buscamos el producto y creamos el OrderItem
        for (Long pId : productIds) {
            Product product = productRepository.findById(pId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + pId));

            // Creamos el item 
            Order_item item = new Order_item();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(1); 
            item.setUnitPrice(product.getPrice()); 

            order.addItem(item);
            
            order.setTotalAmount(order.getTotalAmount() + product.getPrice());
        }

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        if (!"PENDENT".equals(order.getOrderStatus().toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se pueden cancelar órdenes PENDENT");
        }

        order.setOrderStatus(orderStatus.CANCELAT);
        return orderMapper.toDTO(orderRepository.save(order));
    }
}
