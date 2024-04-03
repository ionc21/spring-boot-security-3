package com.pluralsight.security.service;

import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.Transaction;
import com.pluralsight.security.entity.Type;
import com.pluralsight.security.exception.PortfolioNotFoundException;
import com.pluralsight.security.exception.TransactionNotFoundException;
import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.repository.CryptoCurrencyRepository;
import com.pluralsight.security.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PortfolioCommandServiceImpl implements PortfolioCommandService {

	private final PortfolioRepository portfolioRepository;
	private final CryptoCurrencyRepository currencyRepository;

	public PortfolioCommandServiceImpl(PortfolioRepository portfolioRepository, CryptoCurrencyRepository currencyRepository) {
		this.portfolioRepository = portfolioRepository;
		this.currencyRepository = currencyRepository;
	}

	@Override
	public void addTransactionToPortfolio(AddTransactionToPortfolioDto request) {
		Portfolio portfolio = portfolioRepository.findByUserId(request.getUsername()).orElseThrow(PortfolioNotFoundException::new);;
		Transaction transaction = createTransactionEntity(request).orElseThrow(TransactionNotFoundException::new);;;
		portfolio.addTransaction(transaction);
		portfolioRepository.save(portfolio);
	}
	
	@Override
	public void removeTransactionFromPortfolio(String username, String transactionId) {
		Portfolio portfolio = portfolioRepository.findByUserId(username).orElseThrow(PortfolioNotFoundException::new);
		Transaction transaction = portfolio.getTransactionById(transactionId).orElseThrow(TransactionNotFoundException::new);;
		portfolio.deleteTransaction(transaction);
		portfolioRepository.save(portfolio);
	}

	@Override
	public void createNewPortfolio(String userId) {
		portfolioRepository.save(new Portfolio(userId, new ArrayList<>()));
	}
	
	private Optional<Transaction> createTransactionEntity(AddTransactionToPortfolioDto request) {
		return currencyRepository.findBySymbol(request.getCryptoSymbol()).map(cryptoCurrency -> {
			Type type = Type.valueOf(request.getTransactionType());
			BigDecimal quantity = new BigDecimal(request.getQuantity());
			BigDecimal price = new BigDecimal(request.getPrice());
			return new Transaction(cryptoCurrency, type, quantity, price, System.currentTimeMillis());
		});
	}

}
