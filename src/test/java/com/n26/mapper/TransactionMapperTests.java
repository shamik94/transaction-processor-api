package com.n26.mapper;

import com.n26.model.Transaction;
import com.n26.response.TransactionResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class TransactionMapperTests {

    @InjectMocks
    TransactionMapper transactionMapper;

    @Test
    public void TC001_mapNoErrors() {
        String amount = "123.00", timestamp = "2022-07-17T09:59:51.312Z";
        TransactionResponse transactionResponse = TransactionResponse.builder().amount(amount).timestamp(timestamp).build();
        Transaction transaction = transactionMapper.map(transactionResponse);

        assertEquals(new BigDecimal(amount), transaction.getAmount());
        assertEquals(ZonedDateTime.parse(timestamp), transaction.getTimestamp());
    }

    @Test
    public void TC002_mapParseableErrorAmount() {
        String amount = "abc", timestamp = "2022-07-17T09:59:51.312Z";
        TransactionResponse transactionResponse = TransactionResponse.builder().amount(amount).timestamp(timestamp).build();
        try {
            transactionMapper.map(transactionResponse);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Could not parse JSON"));
        }
    }

    @Test
    public void TC002_mapParseableDateAmount() {
        String amount = "123", timestamp = "February";
        TransactionResponse transactionResponse = TransactionResponse.builder().amount(amount).timestamp(timestamp).build();
        try {
            transactionMapper.map(transactionResponse);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Could not parse JSON"));
        }
    }
}
