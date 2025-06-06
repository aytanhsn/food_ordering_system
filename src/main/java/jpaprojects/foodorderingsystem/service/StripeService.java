package jpaprojects.foodorderingsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import jpaprojects.foodorderingsystem.dtos.request.StripePaymentRequest;
import jpaprojects.foodorderingsystem.dtos.response.StripePaymentResponse;
import jpaprojects.foodorderingsystem.entity.Order;
import jpaprojects.foodorderingsystem.entity.Payment;
import jpaprojects.foodorderingsystem.entity.User;
import jpaprojects.foodorderingsystem.enums.PaymentMethod;
import jpaprojects.foodorderingsystem.enums.PaymentStatus;
import jpaprojects.foodorderingsystem.exception.ResourceNotFoundException;
import jpaprojects.foodorderingsystem.repository.OrderRepository;
import jpaprojects.foodorderingsystem.repository.PaymentRepository;
import jpaprojects.foodorderingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StripeService {

    private final EmailSenderService emailSenderService;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public StripePaymentResponse createCheckoutSession(StripePaymentRequest request) throws StripeException {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order tapılmadı: " + request.getOrderId()));

        Long amountInCents = order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(request.getSuccessUrl())
                .setCancelUrl(request.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(request.getCurrency())
                                                .setUnitAmount(amountInCents)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Food Order Payment - Order ID: " + order.getId())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);

        Payment payment = Payment.builder()
                .order(order)
                .customer(order.getCustomer())
                .amount(order.getTotalAmount())
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .status(PaymentStatus.PENDING)
                .transactionId(session.getId())
                .build();

        paymentRepository.save(payment);

        System.out.println("Stripe session ID: " + session.getId());

        return new StripePaymentResponse(session.getUrl());
    }

    public PaymentStatus checkPaymentStatus(String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);

        Payment payment = paymentRepository.findByTransactionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment tapılmadı: " + sessionId));

        if ("paid".equals(session.getPaymentStatus())) {
            payment.setStatus(PaymentStatus.SUCCESS);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);
        return payment.getStatus();
    }

    public void handleStripeWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            System.out.println("Stripe event tipi: " + event.getType());

            if ("checkout.session.completed".equals(event.getType())) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(payload);
                String sessionId = root.path("data").path("object").path("id").asText();

                System.out.println("Webhook session ID: " + sessionId);

                Payment payment = paymentRepository.findByTransactionId(sessionId).orElse(null);
                if (payment != null && payment.getStatus() == PaymentStatus.PENDING) {
                    payment.setStatus(PaymentStatus.SUCCESS);
                    paymentRepository.save(payment);

                    User customer = payment.getCustomer();
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(customer.getEmail());
                    message.setSubject("Ödəniş Təsdiqi");
                    message.setText("Salam " + customer.getFirstName() + ",\n\n" +
                            "Sizin sifariş üçün ödəniş (" + payment.getAmount() + ") uğurla həyata keçirildi.\n\nTəşəkkür edirik!");
                    emailSenderService.send(message);

                    System.out.println("Ödəniş tamamlandı və DB yeniləndi: " + sessionId);
                } else {
                    System.out.println("Payment DB-də tapılmadı və ya artıq SUCCESS-dir.");
                }
            }
        } catch (Exception e) {
            System.out.println("Stripe webhook xətası: " + e.getMessage());
        }
    }
}