package com.pluralsight.security.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DummyPricingService implements PricingService{

    @Override
    public BigDecimal getCurrentPriceForCrypto(String symbol) {
        if(symbol.equals("BTC")) {
            return new BigDecimal("15001.00");
        } else {
            return new BigDecimal("104.00");
        }
    }

}
