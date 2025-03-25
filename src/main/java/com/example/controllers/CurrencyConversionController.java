package com.example.controllers;

import com.example.dto.ConversionRequest;
import com.example.dto.ConversionResponse;
import com.example.services.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService conversionService;

    @PostMapping("/convert")
    public ConversionResponse convertCurrency(@RequestBody ConversionRequest request) {
        return conversionService.convertCurrency(request);
    }
}
