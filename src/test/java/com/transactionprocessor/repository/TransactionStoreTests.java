package com.transactionprocessor.repository;

import com.transactionprocessor.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class TransactionStoreTests {

    @InjectMocks
    TransactionStore transactionStore;

    @Test
    public void TC001_insertTransactions() {
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(10)).timestamp(ZonedDateTime.now()).build());
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(20)).timestamp(ZonedDateTime.now()).build());
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(30)).timestamp(ZonedDateTime.now()).build());

        List<Transaction> transactions = transactionStore.getAllTransactions();

        assertEquals(3, transactions.size());
    }

    @Test
    public void TC002_deleteBeforeTimeStamp() {
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(10)).timestamp(ZonedDateTime.now().minusSeconds(61)).build());
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(20)).timestamp(ZonedDateTime.now()).build());
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(30)).timestamp(ZonedDateTime.now()).build());

        transactionStore.deleteTransactionsBeforeTimeStamp(ZonedDateTime.now());
        List<Transaction> transactions = transactionStore.getAllTransactions();

        assertEquals(2, transactions.size());
    }

    @Test
    public void TC003_deleteAllTransactions() {
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(10)).timestamp(ZonedDateTime.now().minusSeconds(61)).build());
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(20)).timestamp(ZonedDateTime.now()).build());
        transactionStore.insertTransaction(Transaction.builder().amount(new BigDecimal(30)).timestamp(ZonedDateTime.now()).build());

        transactionStore.deleteTransactions();
        List<Transaction> transactions = transactionStore.getAllTransactions();

        assertEquals(0, transactions.size());
    }
}
