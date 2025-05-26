package com.capstone.cartcheckout.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private String productId;
    private String productName; // Could fetch from a product service
    private double price;      // Could fetch from a product service
    private int quantity;
    private double itemTotal;
}
