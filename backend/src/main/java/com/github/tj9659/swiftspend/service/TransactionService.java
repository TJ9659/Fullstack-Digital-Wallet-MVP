package com.github.tj9659.swiftspend.service;

import com.github.tj9659.swiftspend.dto.DashboardDto;
import com.github.tj9659.swiftspend.dto.TopUpRequest;
import com.github.tj9659.swiftspend.dto.TransactionDto;
import com.github.tj9659.swiftspend.dto.TransferDto;
import com.github.tj9659.swiftspend.enums.TransactionType;
import com.github.tj9659.swiftspend.enums.TransferStatus;
import com.github.tj9659.swiftspend.exceptions.AccountNotFoundException;
import com.github.tj9659.swiftspend.exceptions.EmailTakenException;
import com.github.tj9659.swiftspend.exceptions.InvalidAmountException;
import com.github.tj9659.swiftspend.exceptions.SameAccountException;
import com.github.tj9659.swiftspend.model.Account;
import com.github.tj9659.swiftspend.model.Transaction;
import com.github.tj9659.swiftspend.model.User;
import com.github.tj9659.swiftspend.repository.AccountRepository;
import com.github.tj9659.swiftspend.repository.TransactionRepository;
import com.github.tj9659.swiftspend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;
    Pageable topTen = PageRequest.of(0, 10);

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionDto mapToDto(Transaction transaction, User currentUser) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setTimestamp(transaction.getTimestamp().toString());
        dto.setDescription(transaction.getDescription());
        if (transaction.getSenderAccount() != null &&
                transaction.getSenderAccount().getUser().getId().equals(currentUser.getId())) {
            dto.setType("DEBIT");
            dto.setCategory("EXPENSE");
            String recipientName = (transaction.getRecipientAccount() != null)
                    ? transaction.getRecipientAccount().getUser().getName()
                    : "External Party";
            dto.setTitle("Transfer to " + recipientName);
        }

        else if (transaction.getRecipientAccount() != null &&
                transaction.getRecipientAccount().getUser().getId().equals(currentUser.getId())) {

            dto.setType("CREDIT");

            if (transaction.getSenderAccount() == null) {
                dto.setCategory("TOP-UP");

                dto.setTitle(transaction.getDescription() != null && transaction.getDescription().contains("Top-up")
                        ? transaction.getDescription()
                        : "Money Added");
            } else {
                dto.setCategory("INCOME");
                dto.setTitle("Received from " + transaction.getSenderAccount().getUser().getName());
            }
        }
        else {
            dto.setCategory("UNKNOWN");
            dto.setTitle("Transaction #" + transaction.getId());
        }

        return dto;
    }

    public DashboardDto getDashboardData(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account account = user.getAccount();

        DashboardDto dashboard = new DashboardDto();
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();

        dashboard.setAccountNumber(account.getAccountNumber());
        dashboard.setTotalBalance(account.getBalance());

        dashboard.setExpensesToday(orZero(transactionRepository.sumExpenses(account, startOfToday)));
        dashboard.setSavingsToday(orZero(transactionRepository.sumInflow(account, startOfToday)));

        dashboard.setSavingsThisWeek(orZero(transactionRepository.sumInflow(account, startOfWeek)));
        dashboard.setExpensesThisWeek(orZero(transactionRepository.sumExpenses(account, startOfWeek)));

        dashboard.setSavingsThisMonth(orZero(transactionRepository.sumInflow(account, startOfMonth)));
        dashboard.setExpensesThisMonth(orZero(transactionRepository.sumExpenses(account, startOfMonth)));

        dashboard.setTotalSavings(orZero(transactionRepository.sumInflowOverall(account)));
        dashboard.setTotalExpenses(orZero(transactionRepository.sumExpensesOverall(account)));

        List<Transaction> recent = transactionRepository.findRecentTransactions(account, topTen);
        dashboard.setRecentActivities(recent.stream()
                .map(tx -> mapToDto(tx, user))
                .toList());

        return dashboard;
    }

    // helper to prevent NullPointerExceptions if there are no transactions yet
    private BigDecimal orZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }


    @Transactional
    public void processTransfer(String email, TransferDto request){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account sender = accountRepository.findByUser(user)
                .orElseThrow(()-> new AccountNotFoundException("Sender account does not exists."));
        Account recipient = accountRepository.findByAccountNumber(request.getRecipientAccountNumber())
                .orElseThrow(()-> new AccountNotFoundException("Receiver account " + request.getRecipientAccountNumber() + " not found."));

        if(sender.getAccountNumber().equals(recipient.getAccountNumber())){
            throw new SameAccountException("Cannot transfer money to the same account.");
        }

        if(sender.getBalance().compareTo(request.getAmount()) <= 0){
            throw new InvalidAmountException("You do not have enough balance to complete this transfer.");
        }

        BigDecimal oldBalance = sender.getBalance();


        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        recipient.setBalance(recipient.getBalance().add(request.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(recipient);

        Transaction transaction = new Transaction(sender, recipient, request.getAmount(), TransactionType.DEBIT, request.getDescription(), LocalDateTime.now(), oldBalance);

        transactionRepository.save(transaction);
    }

    @Transactional
    public void processTopUp(String email, TopUpRequest dto){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (dto.getCardNumber() == null || dto.getCardNumber().length() != 16) {
            throw new IllegalArgumentException("Invalid card number. Must be 16 digits.");
        }

        if (dto.getExpiry() == null) {
            throw new IllegalArgumentException("Invalid expiry date.");
        }

        if (dto.getCvv() == null || dto.getCvv().length() != 3) {
            throw new IllegalArgumentException("Invalid CVV.");
        }
        Account account = accountRepository.findByUser(user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(dto.getAmount()));
        accountRepository.save(account);

        Transaction tx = new Transaction(
                null,
                account,
                dto.getAmount(),
                TransactionType.CREDIT,
                "Top-up via " + dto.getBankName(),
                LocalDateTime.now(),
                account.getBalance()
        );

        transactionRepository.save(tx);
    }

    public Page<TransactionDto> getTransactionHistory(User currentUser, int page, int size) {
        Account account = accountRepository.findByUser(currentUser)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());

        return transactionRepository.findBySenderAccountOrRecipientAccount(account, account, pageable)
                .map(tx -> mapToDto(tx, currentUser));
    }

//    public ResponseEntity<?> getTransactionsData(){
//
//    }
}
