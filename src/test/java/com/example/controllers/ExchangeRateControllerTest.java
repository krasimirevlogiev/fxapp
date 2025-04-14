package com.example.controllers;

import com.example.config.TestConfig;
import com.example.dto.ExchangeRateResponse;
import com.example.exception.ExternalServiceException;
import com.example.repository.ConversionTransactionRepository;
import com.example.services.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRateController.class)
@Import(TestConfig.class) 
public class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @MockBean
    private ConversionTransactionRepository conversionTransactionRepository;

    @Test
    public void testGetExchangeRate() throws Exception {
        ExchangeRateResponse mockResponse = new ExchangeRateResponse(
            "USD", 
            "EUR", 
            new BigDecimal("0.85")
        );
        
        when(exchangeRateService.getExchangeRate(anyString(), anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/exchange-rate/USD/EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("EUR"))
                .andExpect(jsonPath("$.rate").value(0.85))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testGetExchangeRateWithError() throws Exception {
        when(exchangeRateService.getExchangeRate(anyString(), anyString()))
            .thenThrow(new ExternalServiceException("Invalid currency code"));

        mockMvc.perform(get("/api/exchange-rate/USD/XXX"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("Fixer API error: Invalid currency code"))
                .andExpect(jsonPath("$.status").value(503));
    }
}