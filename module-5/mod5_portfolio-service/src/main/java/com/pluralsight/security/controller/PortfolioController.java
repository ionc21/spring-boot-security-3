package com.pluralsight.security.controller;

import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.model.TransactionDetailsDto;
import com.pluralsight.security.service.PortfolioCommandService;
import com.pluralsight.security.service.PortfolioQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PortfolioController {

	private final PortfolioQueryService portfolioService;
	private final PortfolioCommandService commandService;

	public PortfolioController(PortfolioQueryService portfolioService, PortfolioCommandService commandService) {
		this.portfolioService = portfolioService;
		this.commandService = commandService;
	}

	@GetMapping("/portfolio")
	public PortfolioPositionsDto portfolioPositions(@AuthenticationPrincipal Jwt token) {
		return portfolioService.getPortfolioPositionsForUser(token.getSubject());
	}
	
	@RequestMapping(method= RequestMethod.HEAD,value="/portfolio")
	public ResponseEntity<?> userPortfolioExists(@AuthenticationPrincipal Jwt token) {
		if(this.portfolioService.userHasAportfolio(token.getSubject())) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = {"/portfolio/transactions","/portfolio/transactions/{symbol}"})
	public List<TransactionDetailsDto> getTransactionDetails(@PathVariable Optional<String> symbol, @AuthenticationPrincipal Jwt token) {
		Jwt jwt = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return symbol.map(sym -> portfolioService.getPortfolioTransactionsForUserANdSymbol(token.getSubject(),sym))
				.orElseGet(() -> portfolioService.getPortfolioTransactionsForUser(token.getSubject()).getTransactions());
	}
	
	@PostMapping("/portfolio/transactions")
	public void addTransactionToPortfolio(@RequestBody AddTransactionToPortfolioDto request, @AuthenticationPrincipal Jwt token) {
		request.setUsername(token.getSubject());
		commandService.addTransactionToPortfolio(request);
	}
	
	@DeleteMapping("/portfolio/transactions/{id}")
	public void deleteTransactionFromPortfolio(@PathVariable String id, @AuthenticationPrincipal Jwt token) {
		commandService.removeTransactionFromPortfolio(token.getSubject(),id);
	}
	
	@PutMapping("/portfolio")
	public void createPortfolio(@AuthenticationPrincipal Jwt token) {
		if(!this.portfolioService.userHasAportfolio(token.getSubject())) {
			this.commandService.createNewPortfolio(token.getSubject());
		}
	}
	
}
