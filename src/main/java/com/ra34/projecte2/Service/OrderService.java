package com.ra34.projecte2.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ra34.projecte2.DTO.OrderDTO;
import com.ra34.projecte2.DTO.OrderItemRequest;
import com.ra34.projecte2.DTO.OrderRequest;
import com.ra34.projecte2.Mapper.OrderItemMapper;
import com.ra34.projecte2.Mapper.OrderMapper;
import com.ra34.projecte2.Model.Customer;
import com.ra34.projecte2.Model.Invoice;
import com.ra34.projecte2.Model.Order;
import com.ra34.projecte2.Model.OrderItem;
import com.ra34.projecte2.Model.OrderStatus;
import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Repository.CustomerRepository;
import com.ra34.projecte2.Repository.OrderRepository;
import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;       
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderService (OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper, CustomerRepository customerRepository, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.customerRepository = customerRepository;   
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDTO createOrder(OrderRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer no trobat"));

        // El mapper ja t'ha creat la comanda amb OrderStatus.PENDENT (segons el teu codi)
        Order order = orderMapper.toEntity(request);
        order.setCustomer(customer);
        
        double totalAmount = 0.0;// per omplir després

        if (request.getOrderItems() != null) {
            for (OrderItemRequest itemReq : request.getOrderItems()) {
                // Busquem el producte per ID a la BBDD per assegurar-nos del seu preu i existència
                Product product = productRepository.findById(itemReq.getProductId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producte no trobat amb id: " + itemReq.getProductId()));

                OrderItem item = new OrderItem();
                item.setQuantity(itemReq.getQuantity());
                item.setUnitPrice(product.getPrice()); // Agafem el preu automàticament de la BBDD
                item.setProduct(product);

                totalAmount += (product.getPrice() * itemReq.getQuantity());

                order.addItem(item); // Afegeix a la llista de la comanda i vincula el Order bidireccionalment
            }
        }

        order.setTotalAmount(totalAmount);

        // Creem la factura (Invoice) basant-nos aplicant l'impost (taxAmount) com a percentatge
        if (request.getInvoiceNumber() != null && !request.getInvoiceNumber().isEmpty()) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(request.getInvoiceNumber());
            invoice.setIssueDate(request.getIssueDate());
            invoice.setTaxAmount(request.getTaxAmount());
            
            // Calculem el total amb impostos (per exemple, si ve un 21, sumem el 21% d'IVA)
            double totalWithTax = totalAmount + (totalAmount * request.getTaxAmount() / 100);
            invoice.setTotalWithTax(totalWithTax);

            order.setInvoice(invoice);
        }

        try {
            Order savedOrder = orderRepository.save(order);
            return orderMapper.toDTO(savedOrder);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al guardar la comanda i factura a la base de dades: " + e.getMessage());
        }
    }

    @Transactional
    public OrderDTO processOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comanda no trobada amb id: " + id));

        if (order.getOrderStatus() != OrderStatus.PENDENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Només es poden processar comandes amb estat PENDENT");
        }

        order.setOrderStatus(OrderStatus.PROCESSAT);
        order = orderRepository.save(order);

        return orderMapper.toDTO(order);
    }

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
            OrderItem item = new OrderItem();
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

        order.setOrderStatus(OrderStatus.CANCELAT);
        return orderMapper.toDTO(orderRepository.save(order));
    }
}
