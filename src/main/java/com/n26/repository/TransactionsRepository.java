package com.n26.repository;

import com.n26.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TransactionsRepository {
    void insertTransaction (Transaction transaction);
    void deleteTransactionsBeforeTimeStamp (ZonedDateTime zonedDateTime);
    List<Transaction> getAllTransactions();
    void deleteTransactions();
}
