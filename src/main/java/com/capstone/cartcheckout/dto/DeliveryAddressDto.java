package com.capstone.cartcheckout.dto;

import lombok.Data;

@Data
public class DeliveryAddressDto {
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
