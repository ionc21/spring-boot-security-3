package com.pluralsight.security;

import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.repository.CryptoCurrencyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Mod4PortfolioService {

	private final CryptoCurrencyRepository cryptoRepository;

	public Mod4PortfolioService(CryptoCurrencyRepository cryptoRepository) {
		this.cryptoRepository = cryptoRepository;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void intializeUserData() {		
		CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
		CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");
		cryptoRepository.save(bitcoin);
		cryptoRepository.save(litecoin);
	}

	public static void main(String[] args) {
		SpringApplication.run(Mod4PortfolioService.class, args);
	}
		
}