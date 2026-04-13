package com.github.tj9659.swiftspend.controller;


import com.github.tj9659.swiftspend.dto.DashboardDto;
import com.github.tj9659.swiftspend.dto.TransactionDto;
import com.github.tj9659.swiftspend.model.Account;
import com.github.tj9659.swiftspend.model.User;
import com.github.tj9659.swiftspend.repository.AccountRepository;
import com.github.tj9659.swiftspend.repository.TransactionRepository;
import com.github.tj9659.swiftspend.repository.UserRepository;
import com.github.tj9659.swiftspend.service.AccountService;
import com.github.tj9659.swiftspend.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private final AccountService accountService;

    private final TransactionService transactionService;

    private final UserRepository userRepository;

    public TransactionController(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService, TransactionService transactionService, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }


    @GetMapping("/history")
    public ResponseEntity<?> getTransactionHistory(Authentication authentication, @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size){
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Page<TransactionDto> response = transactionService.getTransactionHistory(currentUser, page, size);

        return ResponseEntity.ok(response);
    }
}
