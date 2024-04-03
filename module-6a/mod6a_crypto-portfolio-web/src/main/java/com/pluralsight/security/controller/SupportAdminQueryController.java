package com.pluralsight.security.controller;

import com.pluralsight.security.model.SupportQueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupportAdminQueryController {

	private final String supportServiceUrl;
	private final WebClient webClient;

	public SupportAdminQueryController(WebClient webClient, @Value("${services.crypto-portfolio-api.uri}") String supportServiceUrl) {
		this.webClient = webClient;
		this.supportServiceUrl =supportServiceUrl;
	}

	@GetMapping("/support/admin")
	public ModelAndView getSupportQueries(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		SupportQueryResponse[] userSupportQueries = this.webClient.get().uri(supportServiceUrl +"/support/admin")
	  			 .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
				 .retrieve()
				 .bodyToMono(SupportQueryResponse[].class)
				 .block();
		return new ModelAndView("support","queries",userSupportQueries);
	}
	
}
