package com.example.controllers;

import com.example.dto.ConversionRequest;
import com.example.dto.ConversionResponse;
import com.example.services.CurrencyConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Currency Conversion", description = "Currency Conversion API")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService conversionService;

    @PostMapping("/convert")
    @Operation(
        summary = "Convert currency",
        description = "Converts an amount from one currency to another using current exchange rates",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successful conversion",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConversionResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Error fetching exchange rate")
        }
    )
    public ConversionResponse convertCurrency(@RequestBody ConversionRequest request) {
        return conversionService.convertCurrency(request);
    }
}