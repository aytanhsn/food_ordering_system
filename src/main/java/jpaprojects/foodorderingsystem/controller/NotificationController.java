package jpaprojects.foodorderingsystem.controller;

import jpaprojects.foodorderingsystem.dtos.request.CourierNotificationEmailDTO;
import jpaprojects.foodorderingsystem.dtos.request.OrderStatusEmailDTO;
import jpaprojects.foodorderingsystem.dtos.request.PaymentConfirmationEmailDTO;
import jpaprojects.foodorderingsystem.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailSenderService emailSenderService;

    @PostMapping("/order-status")
    public ResponseEntity<String> sendOrderStatusEmail(@RequestBody OrderStatusEmailDTO emailDTO) {
        emailSenderService.sendOrderStatusEmail(emailDTO);
        return ResponseEntity.ok("Order status email sent successfully.");
    }

    @PostMapping("/payment-confirmation")
    public ResponseEntity<String> sendPaymentConfirmationEmail(@RequestBody PaymentConfirmationEmailDTO emailDTO) {
        emailSenderService.sendPaymentConfirmationEmail(emailDTO);
        return ResponseEntity.ok("Payment confirmation email sent successfully.");
    }

    @PostMapping("/courier-notification")
    public ResponseEntity<String> sendCourierNotificationEmail(@RequestBody CourierNotificationEmailDTO emailDTO) {
        emailSenderService.sendCourierNotificationEmail(emailDTO);
        return ResponseEntity.ok("Courier notification email sent successfully.");
    }
}
