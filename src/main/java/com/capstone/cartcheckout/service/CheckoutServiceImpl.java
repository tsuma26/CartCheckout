package com.capstone.cartcheckout.service;

import com.capstone.cartcheckout.dto.CartDto;
import com.capstone.cartcheckout.dto.DeliveryAddressDto;
import com.capstone.cartcheckout.dto.PaymentMethodDto;
import com.capstone.cartcheckout.repository.CartCacheRepository;
import com.capstone.cartcheckout.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CartService cartService;
    private final CartRepository cartRepository;
    private final CartCacheRepository cartCacheRepository;
    // In a real application, you would have services for order processing, payment, etc.

    @Override
    @Transactional
    public void checkout(String userId, DeliveryAddressDto deliveryAddress, PaymentMethodDto paymentMethod) {
        Optional<CartDto> cartDtoOptional = cartService.getCart(userId);
        if (cartDtoOptional.isPresent()) {
            CartDto cartDto = cartDtoOptional.get();
            if (!cartDto.getItems().isEmpty()) {
                // 1. Process the order (create an order entity, save it to a database)
                System.out.println("Processing order for user: " + userId);
                System.out.println("Items: " + cartDto.getItems());
                System.out.println("Total Amount: " + cartDto.getTotalAmount());
                System.out.println("Delivery Address: " + deliveryAddress);
                System.out.println("Payment Method: " + paymentMethod);

                // 2. Initiate payment processing (integrate with a payment gateway)
                System.out.println("Initiating payment...");

                // 3. Clear the user's cart
                cartRepository.findByUserId(userId).ifPresent(cart -> cartRepository.delete(cart));
                cartCacheRepository.deleteCart(userId);

                System.out.println("Checkout completed successfully.");
            } else {
                throw new IllegalStateException("Cannot checkout with an empty cart.");
            }
        } else {
            throw new IllegalStateException("Cart not found for user: " + userId);
        }
    }
}
