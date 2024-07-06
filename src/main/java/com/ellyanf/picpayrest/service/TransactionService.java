package com.ellyanf.picpayrest.service;

import com.ellyanf.picpayrest.domain.Transaction;
import com.ellyanf.picpayrest.domain.User;
import com.ellyanf.picpayrest.dto.TransactionDTO;
import com.ellyanf.picpayrest.enums.UserType;
import com.ellyanf.picpayrest.event.TransactionCompletedEvent;
import com.ellyanf.picpayrest.exception.ForbiddenOperationException;
import com.ellyanf.picpayrest.repository.TransactionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    @Autowired
    public TransactionService(
            ApplicationEventPublisher applicationEventPublisher,
            TransactionRepository transactionRepository,
            UserService userService,
            AuthorizationService authorizationService
    ) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    public Transaction newTransaction(@NotNull TransactionDTO transactionDTO) {
        User payer = userService.findByDocument(transactionDTO.getPayerDocument());
        User payee = userService.findByDocument(transactionDTO.getPayeeDocument());

        validateConditions(payer, transactionDTO.getAmount());

        if (!authorizationService.isTransactionAuthorized(transactionDTO)) {
            throw new ForbiddenOperationException("Transaction not authorized.");
        }

        Transaction transaction = new Transaction();
        transaction.setPayer(payer);
        transaction.setPayee(payee);
        transaction.setAmount(transactionDTO.getAmount());

        updateUserBalances(payer, payee, transactionDTO.getAmount());

        transactionRepository.save(transaction);
        applicationEventPublisher.publishEvent(new TransactionCompletedEvent(this, payee.getEmail(), "Transaction received."));

        return transaction;
    }

    private void validateConditions(@NotNull User payer, BigDecimal amount) {
        if (payer.getUserType().equals(UserType.MERCHANT)) {
            throw new ForbiddenOperationException("Merchants cannot carry out transactions.");
        }

        if (amount.compareTo(payer.getBalance()) > 0) {
            throw new ForbiddenOperationException("Insufficient funds. Transaction can not be carried out.");
        }
    }

    private void updateUserBalances(@NotNull User payer, @NotNull User payee, BigDecimal value) {
        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));

        userService.saveUser(payer);
        userService.saveUser(payee);
    }
}
