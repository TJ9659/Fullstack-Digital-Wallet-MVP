package com.github.tj9659.swiftspend.repository;

import com.github.tj9659.swiftspend.model.Account;
import com.github.tj9659.swiftspend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, PagingAndSortingRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByUser(User user);
}
