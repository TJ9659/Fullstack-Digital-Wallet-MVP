package com.github.tj9659.swiftspend.service;

import com.github.tj9659.swiftspend.dto.UpdatePasswordRequestDto;
import com.github.tj9659.swiftspend.dto.UpdateProfileRequestDto;
import com.github.tj9659.swiftspend.dto.UserProfileDto;
import com.github.tj9659.swiftspend.exceptions.InvalidPasswordException;
import com.github.tj9659.swiftspend.exceptions.ResourceNotFoundException;
import com.github.tj9659.swiftspend.exceptions.SamePasswordException;
import com.github.tj9659.swiftspend.model.User;
import com.github.tj9659.swiftspend.repository.AccountRepository;
import com.github.tj9659.swiftspend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserProfileDto updateUserDetails(String email, UpdateProfileRequestDto request){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(request.getName());

        User savedUser = userRepository.save(user);
        return new UserProfileDto(savedUser);

    }

    public UserProfileDto updateUserPassword(String email, UpdatePasswordRequestDto request){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password does not match");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new SamePasswordException("New password cannot be the same as your current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        User savedUser = userRepository.save(user);

        return new UserProfileDto(savedUser);
    }


}
