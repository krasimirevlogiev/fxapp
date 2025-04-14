package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class FixerResponse {
    private boolean success;
    private String base;
    private Map<String, BigDecimal> rates;
    private FixerError error;

    @Data
    public static class FixerError {
        private int code;
        private String info;
    }
}