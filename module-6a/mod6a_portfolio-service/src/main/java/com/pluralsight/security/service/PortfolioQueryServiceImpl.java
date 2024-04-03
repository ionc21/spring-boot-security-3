package com.pluralsight.security.service;

import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.Transaction;
import com.pluralsight.security.exception.PortfolioNotFoundException;
import com.pluralsight.security.exception.TransactionNotFoundException;
import com.pluralsight.security.model.CryptoCurrencyDto;
import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.model.PositionDto;
import com.pluralsight.security.model.TransactionDetailsDto;
import com.pluralsight.security.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioQueryServiceImpl implements PortfolioQueryService {

	private final CurrencyQueryService currencyService;
	private final PortfolioRepository portfolioRepository;
	private final PricingService pricingService;

	public PortfolioQueryServiceImpl(CurrencyQueryService currencyService, PortfolioRepository portfolioRepository, PricingService pricingService) {
		this.currencyService = currencyService;
		this.portfolioRepository = portfolioRepository;
		this.pricingService = pricingService;
	}

	@Override
	public PortfolioPositionsDto getPortfolioPositionsForUser(String username) {
		List<CryptoCurrencyDto> cryptos = currencyService.getSupportedCryptoCurrencies();
		Portfolio portfolio = portfolioRepository.findByUserId(username).orElseThrow(PortfolioNotFoundException::new);;;
		List<PositionDto> positions = calculatePositions(cryptos, portfolio);
		Map<String, String> cryptoMap = convertCryptoListToMap(cryptos);
		return new PortfolioPositionsDto(positions,cryptoMap);
	}

	@Override
	public ListTransactionsDto getPortfolioTransactionsForUser(String username) {
		Portfolio portfolio = this.portfolioRepository.findByUserId(username).orElseThrow(TransactionNotFoundException::new);
		List<Transaction> transactions = portfolio.getTransactions();
		return createListTransactionsResponse(username, transactions);
	}

	@Override
	public List<TransactionDetailsDto> getPortfolioTransactionsForUserANdSymbol(String username, String symbol) {
		return getPortfolioTransactionsForUser(username).getTransactions().stream().filter(trans -> symbol.equals(trans.getSymbol())).collect(Collectors.toList());
	}

	@Override
	public boolean userHasAportfolio(String username) {
		return this.portfolioRepository.existsByUserId(username);
	}
	
	private List<PositionDto> calculatePositions(List<CryptoCurrencyDto> cryptos, Portfolio portfolio) {
		return cryptos.stream().map(crypto -> {
			List<Transaction> cryptoTransactions = portfolio.getTransactionsForCoin(crypto.getSymbol());
			BigDecimal quantity= calculatePositionQuantity(cryptoTransactions);
			BigDecimal currentPrice = getCurrentPriceForCrypto(crypto);
			BigDecimal positionValue = calculatePositionValue(quantity, currentPrice);
			return new PositionDto(crypto, quantity, positionValue);
		}).collect(Collectors.toList());
	}
	
	private BigDecimal getCurrentPriceForCrypto(CryptoCurrencyDto crypto) {
		return pricingService.getCurrentPriceForCrypto(crypto.getSymbol());
	}

	private BigDecimal calculatePositionValue(BigDecimal quantity, BigDecimal currentPrice) {
		return currentPrice.multiply(quantity);
	}
	
	private Map<String, String> convertCryptoListToMap(List<CryptoCurrencyDto> cryptos) {
		return cryptos.stream().collect(Collectors.toMap(CryptoCurrencyDto::getSymbol,CryptoCurrencyDto::getName));
	}
	
	private BigDecimal calculatePositionQuantity(List<Transaction> cryptoTransactions) {
		BigDecimal quantity = BigDecimal.ZERO;
		for(Transaction transaction : cryptoTransactions) {
			switch (transaction.getType()) {
			case BUY:
				quantity = quantity.add(transaction.getQuantity());
				break;
			case SELL:
				quantity = quantity.subtract(transaction.getQuantity());
				break;
			default:
				break;
			}
		}
		return quantity;
	}
	
	private ListTransactionsDto createListTransactionsResponse(String username, List<Transaction> transactions) {
		List<TransactionDetailsDto> transationDetails = transactions.stream()
				.map(transaction -> new TransactionDetailsDto(transaction.getId(), transaction.getCryptoCurrency().getSymbol(), transaction.getType().toString(), transaction.getQuantity().toString(), transaction.getPrice().toString())).collect(Collectors.toList());
		return new ListTransactionsDto(username, transationDetails);
	}
	
}