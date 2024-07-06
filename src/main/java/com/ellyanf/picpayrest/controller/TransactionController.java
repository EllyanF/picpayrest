package com.ellyanf.picpayrest.controller;

import com.ellyanf.picpayrest.domain.Transaction;
import com.ellyanf.picpayrest.dto.TransactionDTO;
import com.ellyanf.picpayrest.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.newTransaction(transactionDTO), HttpStatus.CREATED);
    }
}
