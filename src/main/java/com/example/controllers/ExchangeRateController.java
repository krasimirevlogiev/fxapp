package com.example.controllers;

import com.example.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/exchange-rate/{from}/{to}")
    public Object getExchangeRate(@PathVariable String from, @PathVariable String to) {
        return exchangeRateService.getExchangeRate(from.trim(), to.trim());
    }
}
