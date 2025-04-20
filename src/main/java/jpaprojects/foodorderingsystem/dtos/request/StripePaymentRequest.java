package jpaprojects.foodorderingsystem.dtos.request;

import lombok.Data;

@Data
public class StripePaymentRequest {
    private Long orderId;
    private String currency;
    private String successUrl;
    private String cancelUrl;
}
