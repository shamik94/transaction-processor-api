package com.transactionprocessor.controller;

import com.transactionprocessor.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionHandlerControllerTests {

    @MockBean
    TransactionService transactionService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void TC001_BadRequest() throws Exception {
        Mockito.doThrow(new RuntimeException("Invalid JSON")).when(transactionService).insertTransaction(any(),any());

        mvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content("{\"amount\":\"100.00\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void TC002_UnproccessableEntity() throws Exception {
        Mockito.doThrow(new HttpMessageNotReadableException("Failed to Parse JSON")).when(transactionService).insertTransaction(any(),any());

        mvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content("{\"amount\":\"100.00\",\"timestamp\":\"2022-07-17T09:59:51.312Z\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("Timestamp cannot be in future")));;
    }
}
