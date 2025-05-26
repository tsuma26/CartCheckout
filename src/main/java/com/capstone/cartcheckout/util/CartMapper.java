package com.capstone.cartcheckout.util;

import com.capstone.cartcheckout.dto.CartDto;
import com.capstone.cartcheckout.dto.CartItemDto;
import com.capstone.cartcheckout.model.Cart;
import com.capstone.cartcheckout.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartDto toDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUserId());
        cartDto.setItems(toItemDtoList(cart.getItems()));
        cartDto.setTotalAmount(cart.getTotalAmount());
        return cartDto;
    }

    public List<CartItemDto> toItemDtoList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toItemDto)
                .collect(Collectors.toList());
    }

    public CartItemDto toItemDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(cartItem.getPrice());
        dto.setItemTotal(cartItem.getPrice() * cartItem.getQuantity());
        // In a real application, you would typically fetch productName
        // from a Product entity or service based on the productId.
        // For this example, we'll leave it as null or you could add a placeholder.
        dto.setProductName(null); // Or "Placeholder Product Name"
        return dto;
    }

    public Cart toEntity(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setUserId(cartDto.getUserId());
        cart.setItems(toItemEntityList(cartDto.getItems()));
        cart.setTotalAmount(cartDto.getTotalAmount());
        return cart;
    }

    public List<CartItem> toItemEntityList(List<CartItemDto> cartItemDtos) {
        return cartItemDtos.stream()
                .map(this::toItemEntity)
                .collect(Collectors.toList());
    }

    public CartItem toItemEntity(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setPrice(cartItemDto.getPrice());
        return cartItem;
    }
}
