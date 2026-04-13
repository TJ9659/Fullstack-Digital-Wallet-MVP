package com.github.tj9659.swiftspend.dto;

import com.github.tj9659.swiftspend.model.Account;
import com.github.tj9659.swiftspend.model.User;

import java.math.BigDecimal;

public class AccountProfileDto {
    private String accountNumber;
    private BigDecimal balance;


    public AccountProfileDto(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }
}
