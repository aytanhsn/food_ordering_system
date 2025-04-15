package jpaprojects.foodorderingsystem.controller;

import jpaprojects.foodorderingsystem.dtos.request.DeliveryStatusUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.DeliveryResponseDTO;
import jpaprojects.foodorderingsystem.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@PreAuthorize("hasRole('COURIER')")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/my-orders")
    public List<DeliveryResponseDTO> getMyDeliveries() {
        return deliveryService.getCourierDeliveries();
    }

    @PutMapping("/{deliveryId}/status")
    public DeliveryResponseDTO updateStatus(@PathVariable Long deliveryId,
                                            @RequestBody DeliveryStatusUpdateRequestDTO dto) {
        return deliveryService.updateDeliveryStatus(deliveryId, dto);
    }

    @GetMapping("/{deliveryId}/eta")
    public ResponseEntity<String> getEstimatedTime(@PathVariable Long deliveryId) {
        return ResponseEntity.ok(deliveryService.getEstimatedDeliveryTime(deliveryId));
    }
}
