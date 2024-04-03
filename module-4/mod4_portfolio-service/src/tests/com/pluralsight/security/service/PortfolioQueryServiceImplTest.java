package com.pluralsight.security.service;

import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.Transaction;
import com.pluralsight.security.entity.Type;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.repository.PortfolioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pluralsight.security.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PortfolioQueryServiceImplTest {

    @InjectMocks
    private PortfolioQueryServiceImpl portfolioQueryService;
    @Mock
    private CurrencyQueryService currencyService;
    @Mock
    private PortfolioRepository portfolioRepository;
    @Mock
    private PricingService pricingService;

    @Test
    public void testGetPortfolioPositionsForUser() {
        Mockito.when(currencyService.getSupportedCryptoCurrencies()).thenReturn(CRYPTO_CURRENCIES);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(BTC, Type.BUY, BigDecimal.ONE,BigDecimal.valueOf(15000),1678168212226L));
        transactions.add(new Transaction(LTC, Type.BUY, BigDecimal.TEN,BigDecimal.valueOf(1000),1678168212227L));
        transactions.add(new Transaction(BTC, Type.BUY, BigDecimal.ONE,BigDecimal.valueOf(15000),1678168212228L));
        transactions.add(new Transaction(LTC, Type.SELL, BigDecimal.ONE,BigDecimal.valueOf(90),1678168212229L));
        Portfolio portfolio = new Portfolio("testuser",transactions);
        Mockito.when(portfolioRepository.findByUserId("testuser")).thenReturn(Optional.of(portfolio));
        Mockito.when(pricingService.getCurrentPriceForCrypto("BTC")).thenReturn(BigDecimal.valueOf(15005.00));
        Mockito.when(pricingService.getCurrentPriceForCrypto("LTC")).thenReturn(BigDecimal.valueOf(1050.10));
        PortfolioPositionsDto positionsDto = portfolioQueryService.getPortfolioPositionsForUser("testuser");
        assertEquals(2,positionsDto.getPositions().size());
        assertTrue(BigDecimal.valueOf(30010.0).equals(positionsDto.getPositionForCrypto("BTC").getValue()));
        assertTrue(BigDecimal.valueOf(9450.9).equals(positionsDto.getPositionForCrypto("LTC").getValue()));
    }

}