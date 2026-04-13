package com.github.tj9659.swiftspend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private String recipientAccountNumber;
    private BigDecimal amount;
    private String description;
}
