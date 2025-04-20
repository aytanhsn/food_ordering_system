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
public class DeliveryController {

    private final DeliveryService deliveryService;
    @PreAuthorize("hasRole('COURIER')")
    @GetMapping("/my-orders")
    public List<DeliveryResponseDTO> getMyDeliveries() {
        return deliveryService.getCourierDeliveries();
    }

    @PreAuthorize("hasRole('COURIER')")
    @PutMapping("/{deliveryId}/status")
    public DeliveryResponseDTO updateStatus(@PathVariable Long deliveryId,
                                            @RequestBody DeliveryStatusUpdateRequestDTO dto) {
        return deliveryService.updateDeliveryStatus(deliveryId, dto);
    }

    @PreAuthorize("hasRole('COURIER')")
    @GetMapping("/{deliveryId}/eta")
    public ResponseEntity<String> getEstimatedTime(@PathVariable Long deliveryId) {
        return ResponseEntity.ok(deliveryService.getEstimatedDeliveryTime(deliveryId));
    }
}
