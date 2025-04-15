package jpaprojects.foodorderingsystem.controller;

import jakarta.validation.Valid;
import jpaprojects.foodorderingsystem.dtos.request.OrderRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.OrderResponseDTO;
import jpaprojects.foodorderingsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jpaprojects.foodorderingsystem.enums.OrderStatus;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO response = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrder(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}/status")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
    }

    // ADMIN - sifarişi kuryerə təyin edir
    @PutMapping("/{orderId}/assign-courier")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignCourierToOrder(
            @PathVariable Long orderId,
            @RequestParam Long courierId) {
        orderService.assignCourier(orderId, courierId);
        return ResponseEntity.ok("Kuryer uğurla təyin edildi.");
    }
}
