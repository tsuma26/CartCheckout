package com.capstone.cartcheckout.repository;

import com.capstone.cartcheckout.dto.CartDto;

import java.util.Optional;

public interface CartCacheRepository {
    Optional<CartDto> getCart(String userId);
    void saveCart(String userId, CartDto cartDto);
    void deleteCart(String userId);
}
