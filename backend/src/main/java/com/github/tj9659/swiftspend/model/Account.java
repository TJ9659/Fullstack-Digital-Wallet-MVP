package com.github.tj9659.swiftspend.model;
import com.github.tj9659.swiftspend.enums.AccountType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="accounts")
@Data
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Account(String accountNumber, BigDecimal balance, User user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.user = user;
    }
}
