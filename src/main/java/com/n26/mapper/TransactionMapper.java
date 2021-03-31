package com.n26.mapper;

import com.n26.exception.UnprocessableEntityException;
import com.n26.model.Transaction;
import com.n26.response.TransactionResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Component
public class TransactionMapper {

    public Transaction map(TransactionResponse transactionResponse) {
        try {
            return Transaction.builder()
                    .timestamp(ZonedDateTime.parse(transactionResponse.getTimestamp()))
                    .amount(new BigDecimal(transactionResponse.getAmount()))
                    .build();
        } catch (Exception ex) {
            throw new UnprocessableEntityException("Could not parse JSON");
        }
    }
}
