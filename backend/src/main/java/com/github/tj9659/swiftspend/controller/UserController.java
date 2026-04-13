package com.github.tj9659.swiftspend.controller;

import com.github.tj9659.swiftspend.dto.UpdatePasswordRequestDto;
import com.github.tj9659.swiftspend.dto.UpdateProfileRequestDto;
import com.github.tj9659.swiftspend.dto.UserProfileDto;
import com.github.tj9659.swiftspend.model.User;
import com.github.tj9659.swiftspend.repository.AccountRepository;
import com.github.tj9659.swiftspend.repository.TransactionRepository;
import com.github.tj9659.swiftspend.repository.UserRepository;
import com.github.tj9659.swiftspend.service.AccountService;
import com.github.tj9659.swiftspend.service.TransactionService;
import com.github.tj9659.swiftspend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private final AccountService accountService;

    private final TransactionService transactionService;

    private final UserService userService;

    public UserController(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService, TransactionService transactionService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new UserProfileDto(currentUser));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDto> updateUserDetails(
            @Valid @RequestBody UpdateProfileRequestDto request,
            Authentication authentication
    ) {
        UserProfileDto updatedUser = userService.updateUserDetails(authentication.getName(), request);

        return ResponseEntity.ok(updatedUser);
    }


    @PutMapping("/me/change-password")
    public ResponseEntity<UserProfileDto> changePassword(@RequestBody UpdatePasswordRequestDto request, Authentication authentication){
        UserProfileDto updatedUser = userService.updateUserPassword(authentication.getName(), request);
        return ResponseEntity.ok(updatedUser);
    }

}
