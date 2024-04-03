package com.pluralsight.security.service;

import com.pluralsight.security.model.CryptoCurrencyDto;

import java.util.List;

public interface CurrencyQueryService {

	List<CryptoCurrencyDto> getSupportedCryptoCurrencies();
	CryptoCurrencyDto getCryptoCurrency(String symbol);
}
