package com.github.tj9659.swiftspend.dto;

import com.github.tj9659.swiftspend.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal amount;
    private String type;
    private String category;
    private String timestamp;
}
