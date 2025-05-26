package com.capstone.cartcheckout.repository;

import com.capstone.cartcheckout.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartCacheRepositoryImpl implements CartCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CART_CACHE_KEY_PREFIX = "cart:";

    @Override
    public Optional<CartDto> getCart(String userId) {
        return Optional.ofNullable((CartDto) redisTemplate.opsForValue().get(CART_CACHE_KEY_PREFIX + userId));
    }

    @Override
    public void saveCart(String userId, CartDto cartDto) {
        redisTemplate.opsForValue().set(CART_CACHE_KEY_PREFIX + userId, cartDto);
    }

    @Override
    public void deleteCart(String userId) {
        redisTemplate.delete(CART_CACHE_KEY_PREFIX + userId);
    }
}
