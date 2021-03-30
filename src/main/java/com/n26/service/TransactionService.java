package com.n26.service;

import com.n26.mapper.StatisticsMapper;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Qualifier("transactionStore")
    @Autowired
    private TransactionsRepository transactionsRepo;

    @Autowired
    private StatisticsMapper statisticsMapper;

    private static final Long ZERO = 0L;
    private static final Integer SIXTY = 60;

    public void insertTransaction (Transaction transaction) {

        //TODO Check for future date
        transactionsRepo.insertTransaction(transaction);
    }

    public Statistics getStatistics () {
        transactionsRepo.deleteTransactionsBeforeTimeStamp(ZonedDateTime.now().minusSeconds(SIXTY));

        //TODO format Bigdecimal to 2 decimal places
        // TODO add logs
        List<Transaction> transactions = transactionsRepo.getAllTransactions();

        if (transactions.isEmpty()) {
            return statisticsMapper.map();
        }

        BigDecimal maxVal = transactions.get(0).getAmount()
                , minVal = transactions.get(0).getAmount()
                , avg = new BigDecimal(ZERO)
                , sum = new BigDecimal(ZERO);
        long count = ZERO;


        for (Transaction transaction : transactions) {
            sum = sum.add(transaction.getAmount());
            count++;
            avg = sum.divide(new BigDecimal(count), 2, RoundingMode.HALF_UP);
            maxVal = maxVal.max(transaction.getAmount());
            minVal = minVal.min(transaction.getAmount());
        }

        return statisticsMapper.map(maxVal, minVal, sum, avg, count);
    }

    public void deleteTransactions() {
        transactionsRepo.deleteTransactions();
    }
}
