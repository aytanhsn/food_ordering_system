package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.convertor.DeliveryConverter;
import jpaprojects.foodorderingsystem.dtos.request.DeliveryStatusUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.DeliveryResponseDTO;
import jpaprojects.foodorderingsystem.entity.Delivery;
import jpaprojects.foodorderingsystem.entity.Order;
import jpaprojects.foodorderingsystem.enums.DeliveryStatus;
import jpaprojects.foodorderingsystem.enums.OrderStatus;
import jpaprojects.foodorderingsystem.exception.ResourceNotFoundException;
import jpaprojects.foodorderingsystem.repository.DeliveryRepository;
import jpaprojects.foodorderingsystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;


    public List<DeliveryResponseDTO> getCourierDeliveries() {
        String courierEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return deliveryRepository.findByCourierEmail(courierEmail).stream()
                .map(DeliveryConverter::toDTO)
                .collect(Collectors.toList());
    }

    public DeliveryResponseDTO updateDeliveryStatus(Long deliveryId, DeliveryStatusUpdateRequestDTO dto) {
        String courierEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        if (!delivery.getCourier().getEmail().equals(courierEmail)) {
            throw new RuntimeException("Bu sifariş sənə aid deyil!");
        }

        delivery.setStatus(dto.getStatus());

        if (dto.getStatus() == DeliveryStatus.DELIVERED) {
            LocalDateTime now = LocalDateTime.now();
            delivery.setActualDeliveryTime(now);

            Order order = delivery.getOrder();
            order.setDeliveryTime(now);
            order.setStatus(OrderStatus.DELIVERED); // ✅ Order statusunu DELIVERED et
            orderRepository.save(order);            // ✅ Order-i DB-də güncəllə
        }

        return DeliveryConverter.toDTO(deliveryRepository.save(delivery));
    }

    public String getEstimatedDeliveryTime(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        return "Təxmini çatdırılma vaxtı: " + delivery.getEstimatedTime();
    }
}
