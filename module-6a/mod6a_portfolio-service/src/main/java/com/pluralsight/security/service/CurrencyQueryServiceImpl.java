package com.pluralsight.security.service;

import com.pluralsight.security.model.CryptoCurrencyDto;
import com.pluralsight.security.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyQueryServiceImpl implements CurrencyQueryService {

	private final CryptoCurrencyRepository cryproRepository;
	private Map<String, CryptoCurrencyDto> cryptoCurrencies = null;

	public CurrencyQueryServiceImpl(CryptoCurrencyRepository cryproRepository) {
		this.cryproRepository = cryproRepository;
	}

	@Override
	public List<CryptoCurrencyDto> getSupportedCryptoCurrencies() {
		if(this.cryptoCurrencies == null) {
			this.cryptoCurrencies = new HashMap<>();
			cryproRepository.findAll().forEach(currency -> {
				this.cryptoCurrencies.put(currency.getSymbol(), new CryptoCurrencyDto(currency.getSymbol(), currency.getName()));
			});
		}
		return new ArrayList<>(cryptoCurrencies.values());
	}

	@Override
	public CryptoCurrencyDto getCryptoCurrency(String symbol) {
		return cryptoCurrencies.get(symbol);
	}
	
}
