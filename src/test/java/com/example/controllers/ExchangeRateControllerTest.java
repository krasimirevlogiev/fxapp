package com.example.controllers;

import com.example.config.TestConfig;
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
import java.util.Map;
import org.springframework.context.annotation.Import;
import com.example.config.TestConfig;

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
        Map<String, Object> mockResponse = Map.of(
            "from", "USD",
            "to", "EUR",
            "rate", new BigDecimal("0.85")
        );
        
        when(exchangeRateService.getExchangeRate(anyString(), anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/exchange-rate/USD/EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("EUR"))
                .andExpect(jsonPath("$.rate").value(0.85));
    }

    @Test
    public void testGetExchangeRateWithError() throws Exception {
        Map<String, Object> errorResponse = Map.of(
            "error", "Invalid currency code"
        );
        
        when(exchangeRateService.getExchangeRate(anyString(), anyString())).thenReturn(errorResponse);

        mockMvc.perform(get("/api/exchange-rate/USD/XXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Invalid currency code"));
    }
}