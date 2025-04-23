package jpaprojects.foodorderingsystem.service;
import jakarta.transaction.Transactional;
import jpaprojects.foodorderingsystem.convertor.OrderConverter;
import jpaprojects.foodorderingsystem.dtos.request.CourierNotificationEmailDTO;
import jpaprojects.foodorderingsystem.dtos.request.OrderItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.OrderRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.OrderResponseDTO;
import jpaprojects.foodorderingsystem.entity.*;
import jpaprojects.foodorderingsystem.enums.DeliveryStatus;
import jpaprojects.foodorderingsystem.enums.OrderStatus;
import jpaprojects.foodorderingsystem.enums.Role;
import jpaprojects.foodorderingsystem.exception.ResourceNotFoundException;
import jpaprojects.foodorderingsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final EmailService emailService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DeliveryRepository deliveryRepository;
    private final EmailSenderService emailSenderService;
    @Transactional
    @Cacheable(value = "orderById", key = "#orderId")
    public Order getOrderByIdRaw(Long orderId) {
        return orderRepository.findWithDetailsById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = getOrderByIdRaw(orderId);
        return OrderConverter.toDTO(order);
    }

    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(newStatus);
        orderRepository.save(order);

        // Email göndərmək
        String subject = "Sifariş Statusu Yeniləndi";
        String body = String.format("Hörmətli %s, sifarişinizin statusu %s olaraq yeniləndi.",
                order.getCustomer().getFirstName(), newStatus.name());

        emailService.sendEmail(order.getCustomer().getEmail(), subject, body);
    }


    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .status(OrderStatus.PREPARING)
                .orderTime(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .build();

        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

            BigDecimal itemTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(itemDTO.getQuantity())
                    .price(menuItem.getPrice())
                    .build();

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        return OrderConverter.toDTO(savedOrder);
    }

    @Transactional
    public void assignCourier(Long orderId, Long courierId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User courier = userRepository.findById(courierId)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found"));

        if (courier.getRole() != Role.COURIER) {
            throw new IllegalArgumentException("Təyin edilən istifadəçi kuryer deyil.");
        }

        order.setCourier(courier);
        orderRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(order)
                .courier(courier)
                .status(DeliveryStatus.PICKED_UP)
                .estimatedTime(LocalDateTime.now().plusMinutes(30))
                .build();

        deliveryRepository.save(delivery);

        // ✅ Email göndər (Kuryerə yeni sifariş təyin olundu)
        CourierNotificationEmailDTO emailDTO = CourierNotificationEmailDTO.builder()
                .courierEmail(courier.getEmail())
                .courierName(courier.getFirstName())
                .orderId(order.getId())
                .deliveryAddress(order.getRestaurant().getAddress()) // və ya lazım olan digər address
                .build();

        emailSenderService.sendCourierNotificationEmail(emailDTO);

    }

}
