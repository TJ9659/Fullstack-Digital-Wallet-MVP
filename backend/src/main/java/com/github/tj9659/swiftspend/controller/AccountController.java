package com.github.tj9659.swiftspend.controller;

import com.github.tj9659.swiftspend.dto.*;
import com.github.tj9659.swiftspend.model.Account;
import com.github.tj9659.swiftspend.model.User;
import com.github.tj9659.swiftspend.repository.AccountRepository;
import com.github.tj9659.swiftspend.repository.TransactionRepository;
import com.github.tj9659.swiftspend.repository.UserRepository;
import com.github.tj9659.swiftspend.service.AccountService;
import com.github.tj9659.swiftspend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private final AccountService accountService;

    private final TransactionService transactionService;

    private final UserRepository userRepository;

    public AccountController(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService, TransactionService transactionService, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getDashboardAnalytics(Authentication authentication){
        DashboardDto response = transactionService.getDashboardData(authentication.getName());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/top-up")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> handleTopUp(@RequestBody TopUpRequest request, Authentication authentication) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero");
        }


        transactionService.processTopUp(authentication.getName(), request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> handleTransfer(@RequestBody TransferDto request, Authentication authentication){
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero");
        }

        transactionService.processTransfer(authentication.getName(), request);

        return ResponseEntity.ok().build();

    }


}
