package com.transactionprocessor.repository;

import com.transactionprocessor.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Repository
public class TransactionStore implements TransactionsRepository{

    private final PriorityQueue<Transaction> transactions;

    public TransactionStore () {
        transactions = new PriorityQueue<>((t1, t2) -> {
            if (t1.getTimestamp().isBefore(t2.getTimestamp())) {
                return -1;
            } else if (t1.getTimestamp().isAfter(t2.getTimestamp())) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    @Override
    public synchronized void insertTransaction (Transaction t) {
        transactions.add(t);
    }

    @Override
    public synchronized void deleteTransactionsBeforeTimeStamp(ZonedDateTime zonedDateTime) {
        if (transactions.isEmpty()) return;
        while (transactions.peek() != null && transactions.peek().getTimestamp().isBefore(zonedDateTime)) {
            transactions.poll();
        }
    }

    @Override
    public synchronized List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public synchronized void deleteTransactions() {
        transactions.clear();
    }

}
