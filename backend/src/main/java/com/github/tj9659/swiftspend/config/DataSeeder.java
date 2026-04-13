package com.github.tj9659.swiftspend.config;

import com.github.tj9659.swiftspend.enums.TransactionType;
import com.github.tj9659.swiftspend.model.*;
import com.github.tj9659.swiftspend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import com.github.javafaker.Faker;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      AccountRepository accountRepository,
                      TransactionRepository transactionRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Faker faker = new Faker();
        if (userRepository.count() == 0) {
            String encodedPassword = passwordEncoder.encode("password");

            User testUser = new User();
            testUser.setName("Test User");
            testUser.setEmail("test@example.com");
            testUser.setPassword(encodedPassword);
            userRepository.save(testUser);

            Account testAccount = new Account();
            testAccount.setUser(testUser);
            testAccount.setAccountNumber("6610000000");
            testAccount.setBalance(new BigDecimal("5000.00"));
            accountRepository.save(testAccount);

            for (int i = 1; i <= 20; i++) {
                User user = new User();
                user.setName(faker.name().name());
                user.setEmail(faker.internet().emailAddress());
                user.setPassword(encodedPassword);
                userRepository.save(user);

                Account account = new Account();
                account.setUser(user);
                account.setAccountNumber("6610" + String.format("%06d", i));
                account.setBalance(new BigDecimal("1000.00"));
                accountRepository.save(account);

                createRandomTransactions(account, i, faker);
            }

            createRandomTransactions(testAccount, 0, faker);

            System.out.println("Successfully seeded users, accounts, and transactions.");
        }
    }

    private void createRandomTransactions(Account account, int seed, Faker faker) {
        for (int j = 0; j < 40; j++) {
            // use 3 types: DEPOSIT (Top-up), TRANSFER (to someone else), and maybe a WITHDRAWAL
            BigDecimal amount = new BigDecimal(Math.random() * 500 + 10).setScale(2, RoundingMode.HALF_UP);
            LocalDateTime timestamp = LocalDateTime.now().minusDays(j).minusHours(seed);

            if (j % 3 == 0) {
                Transaction tx = new Transaction(
                        null,
                        account,
                        amount,
                        TransactionType.CREDIT,
                        "Top up via Bank Card",
                        timestamp,
                        account.getBalance()
                );
                transactionRepository.save(tx);

            } else if (j % 3 == 1) {
                Transaction tx = new Transaction(
                        account,
                        null,
                        amount,
                        TransactionType.DEBIT,
                        "ATM Withdrawal",
                        timestamp,
                        account.getBalance()
                );
                transactionRepository.save(tx);

            } else {
                Transaction tx = new Transaction(
                        null,
                        account,
                        amount,
                        TransactionType.CREDIT,
                        "Received from " + faker.name().firstName(),
                        timestamp,
                        account.getBalance()
                );
                transactionRepository.save(tx);
            }
        }
    }
}