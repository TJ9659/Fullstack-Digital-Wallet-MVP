package com.github.tj9659.swiftspend.controller;

import com.github.tj9659.swiftspend.dto.LoginUserDto;
import com.github.tj9659.swiftspend.dto.RegisterUserDto;
import com.github.tj9659.swiftspend.dto.UpdateProfileRequestDto;
import com.github.tj9659.swiftspend.dto.UserProfileDto;
import com.github.tj9659.swiftspend.exceptions.AccountNotFoundException;
import com.github.tj9659.swiftspend.exceptions.EmailTakenException;
import com.github.tj9659.swiftspend.model.User;
import com.github.tj9659.swiftspend.repository.UserRepository;
import com.github.tj9659.swiftspend.responses.LoginResponse;
import com.github.tj9659.swiftspend.service.AuthenticationService;
import com.github.tj9659.swiftspend.service.JwtService;
import com.github.tj9659.swiftspend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    private final UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserRepository userRepository, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody RegisterUserDto registerUserDto, BindingResult result){

        if(userRepository.existsByEmail(registerUserDto.getEmail())){
            throw new EmailTakenException("The email address is already associated with an existing account on that platform");
        }

        User registeredUser = authenticationService.register(registerUserDto);
        String jwtToken = jwtService.generateToken(registeredUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpiration(), new UserProfileDto(registeredUser));

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto){
        User authenticatedUser = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpiration(), new UserProfileDto(authenticatedUser));
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LoginUserDto loginUserDto){
        return ResponseEntity.ok(Map.of("success", true, "message", "Logged out successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication){
        User currentUser = (User) authentication.getPrincipal();

        UserProfileDto response = new UserProfileDto(currentUser);

        return ResponseEntity.ok(response);
    }

}
