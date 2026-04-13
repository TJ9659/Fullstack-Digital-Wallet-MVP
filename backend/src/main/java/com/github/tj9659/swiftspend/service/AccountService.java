package com.github.tj9659.swiftspend.service;

import com.github.tj9659.swiftspend.enums.TransactionType;
import com.github.tj9659.swiftspend.model.Account;
import com.github.tj9659.swiftspend.model.Transaction;
import com.github.tj9659.swiftspend.model.User;
import com.github.tj9659.swiftspend.repository.AccountRepository;
import com.github.tj9659.swiftspend.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    public Account getAccountByUser(User user){
        Account account = accountRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Account not found."));
        return account;
    }

    public Account updateBalance(String accountNumber, BigDecimal amount){
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Error finding account"));
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }


    @Transactional
    public Account topUp(User user, BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new RuntimeException("Invalid Amount.");
        }
        Account account = accountRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Error finding account"));
        BigDecimal oldBalance = account.getBalance();
        BigDecimal newBalance = account.getBalance().add(amount);
        Transaction newTransaction = new Transaction(null, account, amount, TransactionType.CREDIT, "Top Up", LocalDateTime.now(), oldBalance);
        account.setBalance(newBalance);
        transactionRepository.save(newTransaction);
        return accountRepository.save(account);
    }


}
