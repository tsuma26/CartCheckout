package com.capstone.cartcheckout.service;

import com.capstone.cartcheckout.dto.DeliveryAddressDto;
import com.capstone.cartcheckout.dto.PaymentMethodDto;

public interface CheckoutService {
    void checkout(String userId, DeliveryAddressDto deliveryAddress, PaymentMethodDto paymentMethod);
}
