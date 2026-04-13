package com.github.tj9659.swiftspend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpRequest {
    private BigDecimal amount;
    private String cardNumber;
    private String expiry;
    private String cvv;
    private String bankName;
}