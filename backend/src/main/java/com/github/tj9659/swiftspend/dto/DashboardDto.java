package com.github.tj9659.swiftspend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DashboardDto {
    private String accountNumber;
    private BigDecimal totalBalance;

    private BigDecimal totalSavings;
    private BigDecimal savingsToday;
    private BigDecimal savingsThisWeek;
    private BigDecimal savingsThisMonth;


    private BigDecimal totalExpenses;
    private BigDecimal expensesToday;
    private BigDecimal expensesThisWeek;
    private BigDecimal expensesThisMonth;

    private List<TransactionDto> recentActivities;
}
