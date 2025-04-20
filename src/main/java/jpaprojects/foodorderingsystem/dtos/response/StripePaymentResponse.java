package jpaprojects.foodorderingsystem.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StripePaymentResponse {
    private String checkoutUrl;
}

