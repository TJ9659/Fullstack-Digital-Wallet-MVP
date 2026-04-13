package com.github.tj9659.swiftspend.dto;

import com.github.tj9659.swiftspend.model.User;
import lombok.Data;

@Data
public class UserProfileDto {
    private String email;
    private String name;
    private String accountNumber;

    public UserProfileDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.accountNumber = user.getAccount().getAccountNumber();
    }
}
