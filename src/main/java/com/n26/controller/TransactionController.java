package com.n26.controller;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertStatistics (@RequestBody Transaction transaction) {
        transactionService.insertTransaction(transaction);
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
    public void insertStatistics () {
        transactionService.deleteTransactions();
    }
}
