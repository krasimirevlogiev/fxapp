package com.example.controllers;

import com.example.dto.FixerResponse;
import com.example.services.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Tag(name = "Exchange Rates", description = "Exchange Rate API")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/exchange-rate/{from}/{to}")
    @Operation(
        summary = "Get exchange rate",
        description = "Retrieves the current exchange rate between two currencies",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of exchange rate",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FixerResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid currency codes"),
            @ApiResponse(responseCode = "500", description = "Error fetching exchange rate")
        }
    )
    public Object getExchangeRate(
        @Parameter(description = "Source currency code (e.g., USD)") 
        @PathVariable String from, 
        
        @Parameter(description = "Target currency code (e.g., EUR)") 
        @PathVariable String to) {
        return exchangeRateService.getExchangeRate(from.trim(), to.trim());
    }
}