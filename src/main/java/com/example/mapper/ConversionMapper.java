package com.example.mapper;

import com.example.dto.ConversionResponse;
import com.example.entity.ConversionTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring", imports = {BigDecimal.class, RoundingMode.class})
public interface ConversionMapper {

    @Mapping(target = "transactionId", expression = "java(entity.getTransactionId().toString())")
    @Mapping(source = "fromCurrency", target = "from")
    @Mapping(source = "toCurrency", target = "to")
    @Mapping(source = "conversionTime", target = "timestamp")
    @Mapping(target = "exchangeRate", expression = "java(calculateExchangeRate(entity))")
    ConversionResponse toResponse(ConversionTransaction entity);
    
    default BigDecimal calculateExchangeRate(ConversionTransaction entity) {
        if (entity.getOriginalAmount().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return entity.getConvertedAmount()
                .divide(entity.getOriginalAmount(), 6, RoundingMode.HALF_UP);
    }
}