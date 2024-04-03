package com.pluralsight.security;

import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.model.CryptoCurrencyDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestConstants {

    public static final Date CURRENT_DATE_PLUS_5_MIN = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
    public static final Date CURRENT_DATE_MINUS_5_MIN = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(-5));
    public static final List<CryptoCurrencyDto> CRYPTO_CURRENCIES = new ArrayList();
    public static final CryptoCurrency BTC = new CryptoCurrency("BTC", "Bitcoin");
    public static  final CryptoCurrency LTC = new CryptoCurrency("LTC", "Litecoin");
    public static final CryptoCurrencyDto BTC_DTO = new CryptoCurrencyDto("BTC", "Bitcoin");
    public static  final CryptoCurrencyDto LTC_DTO = new CryptoCurrencyDto("LTC", "Litecoin");
    public static final Map<String,String> CRYPTO_CURRENCY_MAP = CRYPTO_CURRENCIES.stream().collect(Collectors.toMap(CryptoCurrencyDto::getSymbol,CryptoCurrencyDto::getName));;

    static {
        CRYPTO_CURRENCIES.add(BTC_DTO);
        CRYPTO_CURRENCIES.add(LTC_DTO);
    }

}
