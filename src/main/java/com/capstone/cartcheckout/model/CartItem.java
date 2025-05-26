package com.capstone.cartcheckout.model;

import lombok.Data;

@Data
public class CartItem {
    private String productId;
    private int quantity;
    private double price; // Price at the time of adding to cart
    // Add other relevant details like productName if needed
}
