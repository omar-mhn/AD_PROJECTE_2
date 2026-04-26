package com.ra34.projecte2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra34.projecte2.DTO.OrderDTO;
import com.ra34.projecte2.Service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

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
