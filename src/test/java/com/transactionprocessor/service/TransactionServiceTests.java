package com.transactionprocessor.service;

import com.transactionprocessor.mapper.StatisticsMapper;
import com.transactionprocessor.model.Transaction;
import com.transactionprocessor.repository.TransactionsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TransactionServiceTests {

    @Mock
    TransactionsRepository transactionsRepository;

    @InjectMocks
    TransactionService transactionService;

    @Spy
    StatisticsMapper statisticsMapper;

    @Test
    public void TC001_insertTransaction() {
        transactionService.insertTransaction(Transaction.builder().build(), UUID.randomUUID());
        verify(transactionsRepository, times(1)).insertTransaction(any());
    }

    @Test
    public void TC002_getStatistics() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction t1 = Transaction.builder().amount(new BigDecimal(10)).timestamp(ZonedDateTime.now()).build();
        Transaction t2 = Transaction.builder().amount(new BigDecimal(20)).timestamp(ZonedDateTime.now()).build();
        Transaction t3 = Transaction.builder().amount(new BigDecimal(30)).timestamp(ZonedDateTime.now()).build();
        transactions.add(t1);
        transactions.add(t2);
        transactions.add(t3);

        Mockito.doReturn(transactions).when(transactionsRepository).getAllTransactions();
        transactionService.getStatistics(UUID.randomUUID());

        verify(transactionsRepository, times(1)).deleteTransactionsBeforeTimeStamp(any());
        verify(statisticsMapper, times(1)).map(
            eq(new BigDecimal(30)),
            eq(new BigDecimal(10)),
            eq(new BigDecimal(60)),
            eq(new BigDecimal(20).setScale(2, BigDecimal.ROUND_HALF_UP)),
            eq(3L));
    }

    @Test
    public void TC003_getStatisticsEmpty() {
        List<Transaction> transactions = new ArrayList<>();

        Mockito.doReturn(transactions).when(transactionsRepository).getAllTransactions();
        transactionService.getStatistics(UUID.randomUUID());

        verify(transactionsRepository, times(1)).deleteTransactionsBeforeTimeStamp(any());
        verify(statisticsMapper, times(1)).map();
    }

    @Test
    public void TC004_deleteStatistics() {
        transactionService.deleteTransactions(UUID.randomUUID());
        verify(transactionsRepository, times(1)).deleteTransactions();
    }
}
