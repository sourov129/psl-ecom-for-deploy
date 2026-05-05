package com.gach.core.service;

import com.gach.core.entity.Order;
import com.gach.core.entity.OrderItem;
import com.gach.core.entity.Product;
import com.gach.core.enums.OrderStatus;
import com.gach.core.enums.ProductStatus;
import com.gach.core.repository.OrderItemRepository;
import com.gach.core.repository.OrderRepository;
import com.gach.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Order createOrder(Order order, List<OrderItem> items) {
        double total = 0;

        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStatus() != ProductStatus.ACTIVE) {
                throw new RuntimeException("Product is not available for order: " + product.getName());
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new RuntimeException("Invalid quantity for product: " + product.getName());
            }
            if (product.getCount() == null || product.getCount() < item.getQuantity()) {
                throw new RuntimeException("Product is out of stock: " + product.getName());
            }

            item.setUnitPrice(product.getPrice());
            item.setSubTotal(product.getPrice() * item.getQuantity());
            total += item.getSubTotal();

            product.setCount(product.getCount() - item.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(total);
        order.setOrderStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        items.forEach(item -> item.setOrderId(savedOrder.getId()));
        orderItemRepository.saveAll(items);

        return savedOrder;
    }

}
