package jpaprojects.foodorderingsystem.controller;

import jpaprojects.foodorderingsystem.dtos.request.PaymentRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.PaymentResponseDTO;
import jpaprojects.foodorderingsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> makePayment(@RequestBody PaymentRequestDTO dto) {
        return ResponseEntity.ok(paymentService.makePayment(dto));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentStatus(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentStatus(id));
    }
}
