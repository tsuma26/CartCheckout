package com.capstone.cartcheckout.service;

import com.capstone.cartcheckout.dto.CartDto;
import com.capstone.cartcheckout.model.Cart;
import com.capstone.cartcheckout.model.CartItem;
import com.capstone.cartcheckout.repository.CartCacheRepository;
import com.capstone.cartcheckout.repository.CartRepository;
import com.capstone.cartcheckout.util.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartCacheRepository cartCacheRepository;
    private final CartMapper cartMapper;
    // Ideally, you would have a ProductService to fetch product details

    @Override
    public CartDto addItem(String userId, String productId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        // In a real application, fetch product details (name, price)
        double productPrice = 10.0; // Placeholder
        String productName = "Sample Product"; // Placeholder

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            newItem.setPrice(productPrice);
            cart.getItems().add(newItem);
        }
        cart.setTotalAmount(calculateTotal(cart.getItems()));
        Cart savedCart = cartRepository.save(cart);
        CartDto cartDto = cartMapper.toDto(savedCart);
        cartCacheRepository.saveCart(userId, cartDto);
        return cartDto;
    }

    @Override
    public CartDto removeItem(String userId, String productId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cart.setTotalAmount(calculateTotal(cart.getItems()));
        Cart savedCart = cartRepository.save(cart);
        CartDto cartDto = cartMapper.toDto(savedCart);
        cartCacheRepository.saveCart(userId, cartDto);
        return cartDto;
    }

    @Override
    public CartDto updateItemQuantity(String userId, String productId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
        cart.setTotalAmount(calculateTotal(cart.getItems()));
        Cart savedCart = cartRepository.save(cart);
        CartDto cartDto = cartMapper.toDto(savedCart);
        cartCacheRepository.saveCart(userId, cartDto);
        return cartDto;
    }

    @Override
    public Optional<CartDto> getCart(String userId) {
        Optional<CartDto> cachedCart = cartCacheRepository.getCart(userId);
        if (cachedCart.isPresent()) {
            return cachedCart;
        }
        Optional<Cart> dbCart = cartRepository.findByUserId(userId);
        return dbCart.map(cartMapper::toDto).map(dto -> {
            cartCacheRepository.saveCart(userId, dto);
            return dto;
        });
    }

    private Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });
    }

    private double calculateTotal(List<CartItem> items) {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
}
