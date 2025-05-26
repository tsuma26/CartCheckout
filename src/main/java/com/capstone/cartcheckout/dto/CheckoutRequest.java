package com.capstone.cartcheckout.dto;

import lombok.Data;

@Data
public class CheckoutRequest {
    private DeliveryAddressDto deliveryAddress;
    private PaymentMethodDto paymentMethod;
}
