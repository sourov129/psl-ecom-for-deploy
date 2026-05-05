package com.gach.core.controller;

import com.gach.core.dto.OrderRequestDto;
import com.gach.core.entity.*;
import com.gach.core.repository.AdminRepository;
import com.gach.core.repository.ProductRepository;
import com.gach.core.repository.UserRepository;
import com.gach.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    // todo: move these to service layer
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequestDto dto) {
        Order order = new Order();
        User user = userRepository.findByEmail(dto.getUserMail()).orElseThrow(() -> new RuntimeException("User not found"));
        order.setUserId(user.getId());

        List<OrderItem> items = dto.getItems().stream().map(i -> {
            OrderItem item = new OrderItem();
            Product product = productRepository.findById(i.getProductId()).get();
            item.setProductId(product.getId());
            item.setQuantity(i.getQuantity());
            return item;
        }).collect(Collectors.toList());

        Order saved = orderService.createOrder(order, items);
        return ResponseEntity.ok(saved);
    }
}
