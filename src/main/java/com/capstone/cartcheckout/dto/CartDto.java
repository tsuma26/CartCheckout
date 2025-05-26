package com.capstone.cartcheckout.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private String userId;
    private List<CartItemDto> items;
    private double totalAmount;
}
