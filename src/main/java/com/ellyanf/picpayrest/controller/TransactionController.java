package com.ellyanf.picpayrest.controller;

import com.ellyanf.picpayrest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
//        return new ResponseEntity<>(transactionService.createTransaction(transactionRequestDTO), HttpStatus.CREATED);
//    }

//    @GetMapping("/auth")
//    public ResponseEntity<String> auth() {
//        Map<String, String> response = transactionService.getTransactionAuthorization();
//        return new ResponseEntity<>(response.get("message"), HttpStatus.OK);
//    }
}
