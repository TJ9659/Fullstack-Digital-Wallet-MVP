package com.github.tj9659.swiftspend.responses;

import com.github.tj9659.swiftspend.dto.LoginUserDto;
import com.github.tj9659.swiftspend.dto.UserProfileDto;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserProfileDto user;

    public LoginResponse(String token, long expiresIn, UserProfileDto user) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.user = user;
    }
}
