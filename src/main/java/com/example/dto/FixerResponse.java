package com.example.dto;

import java.math.BigDecimal;
import java.util.Map;

public class FixerResponse {
    private boolean success;
    private String base;
    private Map<String, BigDecimal> rates;
    private FixerError error;

    public boolean isSuccess() { return success; }
    public String getBase() { return base; }
    public Map<String, BigDecimal> getRates() { return rates; }
    public FixerError getError() { return error; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setBase(String base) { this.base = base; }
    public void setRates(Map<String, BigDecimal> rates) { this.rates = rates; }
    public void setError(FixerError error) { this.error = error; }

    public static class FixerError {
        private int code;
        private String info;

        public int getCode() { return code; }
        public String getInfo() { return info; }
        public void setCode(int code) { this.code = code; }
        public void setInfo(String info) { this.info = info; }
    }
}
