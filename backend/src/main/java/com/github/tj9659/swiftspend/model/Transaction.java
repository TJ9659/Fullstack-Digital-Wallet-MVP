package com.github.tj9659.swiftspend.model;
import com.github.tj9659.swiftspend.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_account_id", nullable = true)
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "recipient_account_id", nullable = true)
    private Account recipientAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String description;

    private LocalDateTime timestamp;

    private BigDecimal postBalance;


    public Transaction(Account senderAccount, Account recipientAccount, BigDecimal amount, TransactionType type, String description, LocalDateTime timestamp, BigDecimal postBalance) {
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.timestamp = timestamp;
        this.postBalance = postBalance;
    }
}
