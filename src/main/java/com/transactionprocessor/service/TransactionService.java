package com.transactionprocessor.service;

import com.transactionprocessor.mapper.StatisticsMapper;
import com.transactionprocessor.response.StatisticsResponse;
import com.transactionprocessor.model.Transaction;
import com.transactionprocessor.repository.TransactionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransactionService {

    @Qualifier("transactionStore")
    @Autowired
    private TransactionsRepository transactionsRepo;

    @Autowired
    private StatisticsMapper statisticsMapper;

    private static final Long ZERO = 0L;
    private static final Integer SIXTY = 60;

    public void insertTransaction(Transaction transaction, UUID key) {
        transactionsRepo.insertTransaction(transaction);
        log.info("Key = {} Successfully inserted transaction", key);
    }

    public StatisticsResponse getStatistics(UUID key) {
        transactionsRepo.deleteTransactionsBeforeTimeStamp(ZonedDateTime.now().minusSeconds(SIXTY));
        log.info("Key = {} Deleting old Transactions", key);
        List<Transaction> transactions = transactionsRepo.getAllTransactions();

        if (transactions.isEmpty()) {
            log.info("Key = {} There are no valid transactions", key);
            return statisticsMapper.map();
        }

        BigDecimal maxVal = transactions.get(0).getAmount(), minVal = transactions.get(0).getAmount(), avg = new BigDecimal(ZERO), sum = new BigDecimal(ZERO);
        long count = ZERO;


        for (Transaction transaction : transactions) {
            sum = sum.add(transaction.getAmount());
            count++;
            avg = sum.divide(new BigDecimal(count), 2, RoundingMode.HALF_UP);
            maxVal = maxVal.max(transaction.getAmount());
            minVal = minVal.min(transaction.getAmount());
        }
        log.info("Key = {} Successfully fetched statistics", key);
        log.info("Key = {} sum = {}, avg = {}, min = {}, max = {}, count = {}", key, sum, avg, minVal, maxVal, count);
        return statisticsMapper.map(maxVal, minVal, sum, avg, count);
    }

    public void deleteTransactions(UUID key) {
        transactionsRepo.deleteTransactions();
        log.info("Key = {} Successfully deleted all Transactions", key);
    }
}
