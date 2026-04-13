package com.github.tj9659.swiftspend.dto;

import lombok.Data;

@Data
public class UpdatePasswordRequestDto {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
