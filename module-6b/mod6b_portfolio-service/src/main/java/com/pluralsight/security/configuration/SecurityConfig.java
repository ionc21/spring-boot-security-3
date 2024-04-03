package com.pluralsight.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 ->
						oauth2
						.jwt(Customizer.withDefaults()))
				.sessionManagement(sm->{
					sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);});
		return http.build();
	}

	@Bean
	public WebClient webClient(ClientRegistrationRepository clientRegistrationRepository,
							   OAuth2AuthorizedClientRepository authorizedClientRepository) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(
						clientRegistrationRepository, authorizedClientRepository);
		oauth2.setDefaultClientRegistrationId("pricing-service");
		return WebClient.builder()
				.apply(oauth2.oauth2Configuration())
				.build();
	}

}
