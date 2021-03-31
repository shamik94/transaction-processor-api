package com.n26.controller;

import com.n26.exception.UnprocessableEntityException;
import com.n26.mapper.TransactionMapper;
import com.n26.model.Transaction;
import com.n26.response.StatisticsResponse;
import com.n26.response.TransactionResponse;
import com.n26.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@Slf4j
public class TransactionController {

    private static final long SIXTY = 60;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionMapper transactionMapper;

    @RequestMapping(value = {"/statistics"}, method = RequestMethod.GET)
    @ResponseBody
    public StatisticsResponse getStatistics() {
        UUID key = UUID.randomUUID();
        log.info("Key = {} Received GET request in Controller", UUID.randomUUID());
        return transactionService.getStatistics(key);
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<?> insertTransaction(@RequestBody @Valid TransactionResponse transactionResponse) {
        UUID key = UUID.randomUUID();
        log.info("Key = {} Received POST request in Controller. Amount = {}, Timestamp = {}", key, transactionResponse.getAmount(), transactionResponse.getTimestamp());
        Transaction transaction = transactionMapper.map(transactionResponse);
        HttpHeaders headers = new HttpHeaders();
        if (transaction.getTimestamp().isBefore(ZonedDateTime.now().minusSeconds(SIXTY))) {
            log.info("Key = {} Timestamp is in before 60 seconds", key);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        if (transaction.getTimestamp().isAfter(ZonedDateTime.now())) {
            log.info("Key = {} Timestamp is in future.", key);
            throw new UnprocessableEntityException("Timestamp cannot be in future.");
        }
        transactionService.insertTransaction(transaction, key);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTransactions() {
        UUID key = UUID.randomUUID();
        log.info("Key = {} Received DELETE request in Controller", key);
        transactionService.deleteTransactions(key);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
