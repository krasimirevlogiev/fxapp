package com.example.controllers;

import com.example.config.TestConfig;
import com.example.dto.ConversionHistoryResponse;
import com.example.dto.ConversionResponse;
import com.example.repository.ConversionTransactionRepository;
import com.example.services.ConversionHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.context.annotation.Import;
import com.example.config.TestConfig;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConversionHistoryController.class)
@Import(TestConfig.class) 
public class ConversionHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversionHistoryService historyService;

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @MockBean
    private ConversionTransactionRepository conversionTransactionRepository;

    @Test
    public void testGetHistoryByTransactionId() throws Exception {
        ConversionResponse conversion = new ConversionResponse(
                "test-id-123",
                "USD",
                "EUR",
                new BigDecimal("100.00"),
                new BigDecimal("85.00")
        );
        
        ConversionHistoryResponse mockResponse = new ConversionHistoryResponse(
                List.of(conversion),
                0,
                10,
                1,
                1
        );
        
        when(historyService.getHistory(eq("test-id-123"), isNull(), anyInt(), anyInt()))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/history")
                .param("transactionId", "test-id-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions[0].transactionId").value("test-id-123"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    public void testGetHistoryByDate() throws Exception {
        ConversionResponse conversion = new ConversionResponse(
                "test-id-123",
                "USD",
                "EUR",
                new BigDecimal("100.00"),
                new BigDecimal("85.00")
        );
        
        ConversionHistoryResponse mockResponse = new ConversionHistoryResponse(
                List.of(conversion),
                0,
                10,
                1,
                1
        );
        
        when(historyService.getHistory(isNull(), any(LocalDate.class), anyInt(), anyInt()))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/history")
                .param("date", "2025-03-26"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions[0].transactionId").value("test-id-123"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    public void testGetHistoryWithoutParams() throws Exception {
        mockMvc.perform(get("/api/history"))
                .andExpect(status().isBadRequest());
    }
}