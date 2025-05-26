package com.capstone.cartcheckout.dto;

import lombok.Data;

@Data
public class PaymentMethodDto {
    private String type; // e.g., "credit_card", "paypal"
    private String details; // Could be a JSON object with card number, etc.
}
