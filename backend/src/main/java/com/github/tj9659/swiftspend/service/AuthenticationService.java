package com.github.tj9659.swiftspend.service;

import com.github.tj9659.swiftspend.dto.LoginUserDto;
import com.github.tj9659.swiftspend.dto.RegisterUserDto;
import com.github.tj9659.swiftspend.dto.UpdateProfileRequestDto;
import com.github.tj9659.swiftspend.dto.UserProfileDto;
import com.github.tj9659.swiftspend.model.Account;
import com.github.tj9659.swiftspend.model.User;
import com.github.tj9659.swiftspend.repository.AccountRepository;
import com.github.tj9659.swiftspend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public User register(@Valid @RequestBody RegisterUserDto request){
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), request.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);
        Account account = new Account();
        account.setAccountNumber(generateRandomAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setUser(savedUser);
        accountRepository.save(account);
        savedUser.setAccount(account);

        return savedUser;
    }

    public User login(LoginUserDto request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found."));
    }


    public UserProfileDto getProfileDetails(User user){

        return new UserProfileDto(
                user
        );
    }

    private String generateRandomAccountNumber(){
        Random random = new Random();
        String accountNumber;

        do {
            Long number = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
            accountNumber = String.valueOf(number);
        } while(accountRepository.findByAccountNumber(accountNumber).isPresent()); // ensures no duplicate account number

        return accountNumber;
    }


}
