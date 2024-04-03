package com.pluralsight.security.repository;

import com.pluralsight.security.entity.CryptoCurrency;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CryptoCurrencyRepository extends CrudRepository<CryptoCurrency, String> {
	Optional<CryptoCurrency> findBySymbol(String symbol);
	
}
