package jpaprojects.foodorderingsystem.controller;

import com.stripe.exception.StripeException;
import jpaprojects.foodorderingsystem.dtos.request.StripePaymentRequest;
import jpaprojects.foodorderingsystem.dtos.response.StripePaymentResponse;
import jpaprojects.foodorderingsystem.enums.PaymentStatus;
import jpaprojects.foodorderingsystem.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe-payments")
@RequiredArgsConstructor
public class StripePaymentController {

    private final StripeService stripeService;
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripePaymentResponse> createCheckoutSession(@RequestBody StripePaymentRequest request) throws StripeException {
        StripePaymentResponse response = stripeService.createCheckoutSession(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/check-status/{sessionId}")
    public ResponseEntity<String> checkStatus(@PathVariable String sessionId) throws StripeException {
        PaymentStatus status = stripeService.checkPaymentStatus(sessionId);
        return ResponseEntity.ok("Ödəniş statusu: " + status);
    }
}
