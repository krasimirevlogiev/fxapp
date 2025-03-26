package com.example.controllers;

import com.example.config.TestConfig;
import com.example.dto.ConversionRequest;
import com.example.dto.ConversionResponse;
import com.example.repository.ConversionTransactionRepository;
import com.example.services.CurrencyConversionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import org.springframework.context.annotation.Import;
import com.example.config.TestConfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyConversionController.class)
@Import(TestConfig.class) 
public class CurrencyConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyConversionService conversionService;

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @MockBean
    private ConversionTransactionRepository conversionTransactionRepository;

    @Test
    public void testConvertCurrency() throws Exception {
        // Setup mock response
        ConversionResponse mockResponse = new ConversionResponse(
                "test-id-123",
                "USD",
                "EUR",
                new BigDecimal("100.00"),
                new BigDecimal("85.00")
        );
        
        when(conversionService.convertCurrency(any(ConversionRequest.class))).thenReturn(mockResponse);

        // Create test request
        ConversionRequest request = new ConversionRequest("USD", "EUR", new BigDecimal("100.00"));
        
        // Perform test
        mockMvc.perform(post("/api/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("test-id-123"))
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("EUR"))
                .andExpect(jsonPath("$.originalAmount").value(100.00))
                .andExpect(jsonPath("$.convertedAmount").value(85.00));
    }
}