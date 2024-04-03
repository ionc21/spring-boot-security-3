package com.pluralsight.security.service;

import com.pluralsight.security.model.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class WebClientPricingService implements PricingService{

	private final WebClient webClient;
	private final String pricingServiceUri;

	public WebClientPricingService(WebClient webClient, @Value("${services.pricing-service.uri}") String pricingServiceUri) {
		this.webClient = webClient;
		this.pricingServiceUri = pricingServiceUri;
	}
	@Override
	public BigDecimal getCurrentPriceForCrypto(String symbol) {
		Price[] prices = webClient.get()
				.uri(pricingServiceUri+"/prices")
				.attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("pricing-service"))
				.retrieve()
				.bodyToMono(Price[].class)
				.block();
		for(Price price : prices) {
			if(price.getSymbol().equals(symbol)) {
				return new BigDecimal(price.getPrice());
			}
		}
		return null;
	}

	public BigDecimal getCurrentPriceForCrypto2(String symbol) {
		Jwt jwt = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Price[] prices = webClient.get()
				.uri(pricingServiceUri)
				.headers(header -> header.setBearerAuth(jwt.getTokenValue()))
				.retrieve()
				.bodyToMono(Price[].class)
				.block();
		for(Price price : prices) {
			if(price.getSymbol().equals(symbol)) {
				return new BigDecimal(price.getPrice());
			}
		}
		return null;
	}

}
