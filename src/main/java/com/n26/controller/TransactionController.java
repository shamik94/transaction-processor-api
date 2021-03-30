package com.n26.controller;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = {"/statistics"}, method = RequestMethod.GET)
    @ResponseBody
    public Statistics getStatistics () {
        return transactionService.getStatistics();
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertTransaction (@RequestBody Transaction transaction) {
        transactionService.insertTransaction(transaction);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTransactions () {
        transactionService.deleteTransactions();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
