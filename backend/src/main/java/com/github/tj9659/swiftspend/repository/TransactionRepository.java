package com.github.tj9659.swiftspend.repository;

import com.github.tj9659.swiftspend.enums.TransactionType;
import com.github.tj9659.swiftspend.model.Account;
import com.github.tj9659.swiftspend.model.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {
//    List<Transaction> findTop10ByAccountOrderByTimestampDesc(Account account);
//    @Query("""
//            Select t from Transaction t WHERE t.senderAccount = :account OR
//            """)
//    Page<Transaction> findAllUserTransactions(Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.senderAccount = :account OR t.recipientAccount = :account ORDER BY t.timestamp DESC")
    List<Transaction> findRecentTransactions(@Param("account") Account account, Pageable pageable);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.senderAccount = :account")
    BigDecimal sumExpensesOverall(@Param("account") Account account);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.recipientAccount = :account")
    BigDecimal sumInflowOverall(@Param("account") Account account);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.senderAccount = :account AND t.timestamp >= :start")
    BigDecimal sumExpenses(@Param("account") Account account, @Param("start") LocalDateTime start);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.recipientAccount = :account AND t.timestamp >= :start")
    BigDecimal sumInflow(@Param("account") Account account, @Param("start") LocalDateTime start);

    Page<Transaction> findBySenderAccountOrRecipientAccount(
            Account sender, Account recipient, Pageable pageable);
    //
    //    @Query("""
    //    SELECT SUM(t.amount)
    //    FROM Transaction t
    //    WHERE t.account = :account
    //    AND t.type = :type
    //    """)
    //    BigDecimal sumAmountByAccountAndType(@Param("account") Account account,
    //                                                     @Param("type") TransactionType type);
    //
    //    @Query("""
    //    SELECT SUM(t.amount)
    //    FROM Transaction t
    //    WHERE t.account = :account
    //    AND t.type = :type
    //    AND t.timestamp BETWEEN :start AND :end
    //    """)
    //    BigDecimal sumAmountByAccountAndTypeAndDateRange(@Param("account") Account account,
    //                                                     @Param("type") TransactionType type,
    //                                                     @Param("start") LocalDateTime start,
    //                                                     @Param("end") LocalDateTime end);

}
