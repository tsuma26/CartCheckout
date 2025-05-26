package com.capstone.cartcheckout.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carts")
@Data
public class Cart {
    @Id
    private String id; // Unique cart ID (could be the userId for simplicity)
    private String userId;
    private List<CartItem> items;
    private double totalAmount;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }
}