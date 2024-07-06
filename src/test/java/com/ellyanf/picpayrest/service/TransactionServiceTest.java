package com.ellyanf.picpayrest.service;

import com.ellyanf.picpayrest.domain.Transaction;
import com.ellyanf.picpayrest.domain.User;
import com.ellyanf.picpayrest.dto.TransactionDTO;
import com.ellyanf.picpayrest.enums.UserType;
import com.ellyanf.picpayrest.exception.ForbiddenOperationException;
import com.ellyanf.picpayrest.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new transaction successfully.")
    void testNewTransaction() {
        User payer = new User();
        payer.setUserType(UserType.COMMON);
        payer.setBalance(BigDecimal.valueOf(1000));

        User payee = new User();
        payee.setBalance(BigDecimal.valueOf(200));

        TransactionDTO transactionDTO = new TransactionDTO(
                "payerDocument",
                "payeeDocument",
                BigDecimal.valueOf(200)
        );

        when(userService.findByDocument("payerDocument")).thenReturn(payer);
        when(userService.findByDocument("payeeDocument")).thenReturn(payee);

        when(authorizationService.isTransactionAuthorized(transactionDTO)).thenReturn(true);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setPayer(payer);
        savedTransaction.setPayee(payee);
        savedTransaction.setAmount(transactionDTO.getAmount());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction transaction = transactionService.newTransaction(transactionDTO);

        assertNotNull(transaction);
        assertEquals(BigDecimal.valueOf(800), payer.getBalance());
        assertEquals(BigDecimal.valueOf(400), payee.getBalance());
        verify(authorizationService, times(1)).isTransactionAuthorized(any(TransactionDTO.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw ForbiddenOperationException considering that authorization fails.")
    void testNewTransactionUnauthorized() {
        User payer = new User();
        payer.setUserType(UserType.COMMON);
        payer.setBalance(BigDecimal.valueOf(1000));

        TransactionDTO transactionDTO = new TransactionDTO("payerDocument", "payeeDocument", BigDecimal.valueOf(200));

        when(userService.findByDocument("payerDocument")).thenReturn(payer);
        when(userService.findByDocument("payeeDocument")).thenReturn(new User());

        when(authorizationService.isTransactionAuthorized(transactionDTO)).thenReturn(false);

        assertThrows(ForbiddenOperationException.class, () -> transactionService.newTransaction(transactionDTO));
        assertEquals(payer.getBalance(), BigDecimal.valueOf(1000));
        verify(authorizationService, times(1)).isTransactionAuthorized(any(TransactionDTO.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw as merchants are not allowed to perform transactions.")
    void testNewTransactionWithMerchantPayer() {
        User payer = new User();
        payer.setUserType(UserType.MERCHANT);
        payer.setBalance(BigDecimal.valueOf(1000));

        TransactionDTO transactionDTO = new TransactionDTO("payerDocument", "payeeDocument", BigDecimal.valueOf(100));

        when(userService.findByDocument("payerDocument")).thenReturn(payer);
        when(userService.findByDocument("payeeDocument")).thenReturn(new User());

        assertThrows(ForbiddenOperationException.class, () -> transactionService.newTransaction(transactionDTO));
        verify(userService, times(2)).findByDocument(anyString());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw if transaction value is higher than")
    void testNewTransactionWithInsufficientFunds() {
        User payer = new User();
        payer.setUserType(UserType.COMMON);
        payer.setBalance(BigDecimal.valueOf(1000));

        TransactionDTO transactionDTO = new TransactionDTO("payerDocument", "payeeDocument", BigDecimal.valueOf(2000));

        when(userService.findByDocument("payerDocument")).thenReturn(payer);
        when(userService.findByDocument("payeeDocument")).thenReturn(new User());

        assertThrows(ForbiddenOperationException.class, () -> transactionService.newTransaction(transactionDTO));
        verify(userService, times(2)).findByDocument(anyString());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
