package com.pluralsight.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class PortfolioService {

	private final WebClient webClient;
	private static final String PORTFOLIO_SERVICE_DOMAIN = "http://localhost:8181";
	
	public boolean userHasPortfolio() {
		ClientResponse response = this.webClient.head().uri(PORTFOLIO_SERVICE_DOMAIN+"/portfolio")
							 .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("crypto-portfolio"))
							 .exchange()
							 .block();
		return response.statusCode().equals(HttpStatus.OK);
	}
	
	public void createPortfolio() {
		this.webClient.put().uri(PORTFOLIO_SERVICE_DOMAIN+"/portfolio")
				 .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("crypto-portfolio"))
				 .exchange()
				 .block();
	}
	
}
