package com.pluralsight.security.controller;

import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.model.DeleteTransactionsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
public class PortfolioCommandController {

	private final WebClient webClient;
	private final String portfolioServiceUrl;

	public PortfolioCommandController(WebClient webClient, @Value("${services.crypto-portfolio-api.uri}") String portfolioServiceUrl) {
		this.webClient = webClient;
		this.portfolioServiceUrl = portfolioServiceUrl;
	}

	@PostMapping("/portfolio/transactions")
	public ModelAndView addTransactionToPortfolio(@ModelAttribute("transaction") AddTransactionToPortfolioDto request, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		URI targetUri = UriComponentsBuilder.fromHttpUrl(portfolioServiceUrl)
		.path("/portfolio/transactions")
		.build().encode().toUri();
		request.setUsername(user.getPreferredUsername());
		this.webClient.post()
		.uri(targetUri)
		 .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
		.body(BodyInserters.fromObject(request))
		.retrieve()
		.bodyToMono(Void.class)
		.block();
		return new ModelAndView("redirect:/portfolio");
	}
		
	@DeleteMapping("/portfolio/transactions")
	public ModelAndView deleteTransactionFromPortfolio(@ModelAttribute("selected") DeleteTransactionsDto request,@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		for(String transactionId : request.getId()) {
			URI targetUri = UriComponentsBuilder.fromHttpUrl(portfolioServiceUrl)
				.path("/portfolio/transactions/"+transactionId)			
				.build().encode().toUri();
				this.webClient.delete()
				.uri(targetUri)
				.attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
				.retrieve()
				.bodyToMono(Void.class)
				.block();
		}
		return new ModelAndView("redirect:/portfolio");
	}
	
}
