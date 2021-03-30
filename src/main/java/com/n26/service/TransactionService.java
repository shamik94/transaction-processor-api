package com.n26.service;

import com.n26.mapper.StatisticsMapper;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Qualifier("transactionStore")
    @Autowired
    private TransactionsRepository transactionsRepo;

    @Autowired
    private StatisticsMapper statisticsMapper;

    private static final Long ZERO = 0L;

    public void insertTransaction (Transaction transaction) {
        transactionsRepo.insertTransaction(transaction);
    }

    public Statistics getStatistics () {
        transactionsRepo.deleteTransactionsBeforeTimeStamp(LocalDateTime.now());
        List<Transaction> transactions = transactionsRepo.getAllTransactions();

        if (transactions.isEmpty()) {
            return statisticsMapper.map();
        }

        BigDecimal maxVal = null, minVal = null, avg = new BigDecimal(ZERO), sum = new BigDecimal(ZERO);
        long count = ZERO;

        for (Transaction transaction : transactions) {
            sum = sum.add(transaction.getAmount());
            count++;
            avg = sum.divide(new BigDecimal(count));
            maxVal = null == maxVal ? transaction.getAmount() : maxVal.max(transaction.getAmount());
            minVal = null == minVal ? transaction.getAmount() : minVal.min(transaction.getAmount());
        }

        return statisticsMapper.map(maxVal, minVal, sum, avg, count);
    }
}
