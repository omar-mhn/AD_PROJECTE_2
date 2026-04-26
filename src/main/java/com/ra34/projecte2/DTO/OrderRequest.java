package com.ra34.projecte2.DTO;

import java.util.List;

import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.Order_item;

public class OrderRequest {

    private long customerId;
    private Order order;
    private List<Order_item> order_item;

    // Constructor
    public OrderRequest(){}

    public OrderRequest(long customerId, Order order, List<Order_item> order_item){
        this.customerId = customerId;
        this.order = order;
        this.order_item = order_item;
    }

    // Getters i setters
    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public List<Order_item> getOrder_item() {
        return order_item;
    }
    public void setOrder_item(List<Order_item> order_item) {
        this.order_item = order_item;
    }

}
