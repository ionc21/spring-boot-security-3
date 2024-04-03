package com.pluralsight.security.service;

import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.model.TransactionDetailsDto;

import java.util.List;

public interface PortfolioQueryService {

	PortfolioPositionsDto getPortfolioPositionsForUser(String username);
	ListTransactionsDto getPortfolioTransactionsForUser(String username);
	List<TransactionDetailsDto> getPortfolioTransactionsForUserANdSymbol(String username, String symbol);
	boolean userHasAportfolio(String username);
}
