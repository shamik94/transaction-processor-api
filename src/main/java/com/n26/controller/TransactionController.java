package com.n26.controller;

import com.n26.model.Statistics;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @RequestMapping(value = {"/statistics"}, method = RequestMethod.GET)
    @ResponseBody
    public Statistics getStatistics() {
        Statistics statistics = Statistics.builder().count(100L).build();
        return statistics;
    }
}
