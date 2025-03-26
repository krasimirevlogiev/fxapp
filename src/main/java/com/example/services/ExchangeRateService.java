package com.example.services;

import com.example.dto.FixerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ExchangeRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${fixer.api.url}")
    private String fixerApiUrl;
    @Value("${fixer.api.key}")
    private String fixerApiKey;

    @Cacheable(value = "exchangeRates", key = "#from + '-' + #to")
    public Map<String, Object> getExchangeRate(String from, String to) {
        if (from.length() != 3 || to.length() != 3) {
            return Map.of("error", "Currency codes must be 3 letters (e.g., USD, EUR).");
        }
        
        if (from.equalsIgnoreCase(to)) {
            return Map.of("from", from.toUpperCase(), 
                          "to", to.toUpperCase(), 
                          "rate", 1.0);
        }

        String url = String.format("%s/latest?access_key=%s&symbols=%s,%s",
                fixerApiUrl, fixerApiKey, from.toUpperCase(), to.toUpperCase());

        try {
            ResponseEntity<FixerResponse> response =
                    restTemplate.getForEntity(url, FixerResponse.class);
            FixerResponse fixerData = response.getBody();

            if (fixerData == null || !fixerData.isSuccess()) {
                String msg = (fixerData != null && fixerData.getError() != null)
                        ? fixerData.getError().getInfo()
                        : "Unknown error from Fixer";
                return Map.of("error", "Fixer API error: " + msg);
            }

            Map<String, BigDecimal> rates = fixerData.getRates();
            String base = fixerData.getBase();  
            BigDecimal resultRate = computeRate(from, to, base, rates);

            return Map.of(
                "from", from.toUpperCase(),
                "to", to.toUpperCase(),
                "rate", resultRate
            );

        } catch (Exception ex) {
            return Map.of("error", "Could not contact Fixer.io: " + ex.getMessage());
        }
    }

    private BigDecimal computeRate(String from, String to, String base, Map<String, BigDecimal> rates) {
        if (base.equalsIgnoreCase(from)) {
            return rates.get(to.toUpperCase());
        }
        else if (base.equalsIgnoreCase(to)) {
            BigDecimal fromRate = rates.get(from.toUpperCase());
            return BigDecimal.ONE.divide(fromRate, 6, RoundingMode.HALF_UP);
        } 
        else {
            BigDecimal rateTo = rates.get(to.toUpperCase());
            BigDecimal rateFrom = rates.get(from.toUpperCase());
            return rateTo.divide(rateFrom, 6, RoundingMode.HALF_UP);
        }
    }
}