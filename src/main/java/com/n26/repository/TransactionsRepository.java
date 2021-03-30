package com.n26.repository;

import com.n26.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionsRepository {
    public void insertTransaction (Transaction transaction);
    public void deleteTransactionsBeforeTimeStamp (LocalDateTime localDateTime);
    public List<Transaction> getAllTransactions();
}
