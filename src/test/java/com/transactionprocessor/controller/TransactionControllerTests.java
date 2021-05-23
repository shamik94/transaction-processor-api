package com.transactionprocessor.controller;

import com.transactionprocessor.response.StatisticsResponse;
import com.transactionprocessor.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTests {


    @MockBean
    TransactionService transactionService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void TC001_getStatistics() throws Exception {
        StatisticsResponse statisticsResponse = StatisticsResponse.builder()
                .count(0L)
                .avg("0.00")
                .sum("0.00")
                .max("0.00")
                .min("0.00")
                .build();

        Mockito.doReturn(statisticsResponse).when(transactionService).getStatistics(any());

        mvc.perform(MockMvcRequestBuilders
                .get("/statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(transactionService).getStatistics(any());
    }

    @Test
    public void TC002_postTransaction() throws Exception {
        ZonedDateTime timestamp = ZonedDateTime.now();
        mvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content("{\"amount\":\"100.00\",\"timestamp\":\"" + timestamp + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Mockito.verify(transactionService).insertTransaction(any(), any());
    }

    @Test
    public void TC003_postTransactionWithOldTimeStamp() throws Exception {
        ZonedDateTime timestamp = ZonedDateTime.now().minusSeconds(61);
        mvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content("{\"amount\":\"100.00\",\"timestamp\":\"" + timestamp + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void TC004_postTransactionWithFutureTimeStamp() throws Exception {
        ZonedDateTime timestamp = ZonedDateTime.now().plusSeconds(1);
        mvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content("{\"amount\":\"100.00\",\"timestamp\":\"" + timestamp + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void TC005_deleteTransactions() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(transactionService).deleteTransactions(any());
    }
}
