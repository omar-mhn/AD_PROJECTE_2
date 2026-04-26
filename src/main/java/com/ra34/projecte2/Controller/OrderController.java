package com.ra34.projecte2.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ra34.projecte2.DTO.OrderDTO;
import com.ra34.projecte2.DTO.OrderRequest;
import com.ra34.projecte2.Service.OrderService;

@Controller
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest request) {
        OrderDTO response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/orders/process/{id}")
    public ResponseEntity<OrderDTO> processOrder(@PathVariable("id") Long id) {
        OrderDTO response = orderService.processOrder(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("orders/{id}")
    public ResponseEntity<OrderDTO> addProducts(@PathVariable Long id, @RequestBody List<Long> productIds) {
        
        OrderDTO response = orderService.addProductsToOrder(id, productIds);
        return ResponseEntity.ok(response);
    }

    @PutMapping("orders/cancel/{id}")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long id) {
        OrderDTO response = orderService.cancelOrder(id);
        return ResponseEntity.ok(response);
    }
}
