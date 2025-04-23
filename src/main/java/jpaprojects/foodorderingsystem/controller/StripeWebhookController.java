package jpaprojects.foodorderingsystem.controller;

import jpaprojects.foodorderingsystem.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe-payments")
@RequiredArgsConstructor
public class StripeWebhookController {

    private final StripeService stripeService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader("Stripe-Signature") String sigHeader) {
        System.out.println("Webhook controller çağırıldı");
        System.out.println("Payload: " + payload);
        System.out.println("Signature: " + sigHeader);

        stripeService.handleStripeWebhook(payload, sigHeader);
        System.out.println("StripeService.handleStripeWebhook() çağırıldı");
        return ResponseEntity.ok("Webhook qəbul edildi");
    }
}
