package com.ellyanf.picpayrest.repository;

import com.ellyanf.picpayrest.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
