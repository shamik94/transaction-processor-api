package com.transactionprocessor.mapper;

import com.transactionprocessor.exception.UnprocessableEntityException;
import com.transactionprocessor.model.Transaction;
import com.transactionprocessor.response.TransactionResponse;
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
