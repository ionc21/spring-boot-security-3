package com.pluralsight.security.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

	private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

	public SecurityConfiguration(JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter) {
		this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
	}

	@Bean
	public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz
						.requestMatchers("/support/admin/**").access(AuthorizationManagers.allOf(
								AuthorityAuthorizationManager.hasAuthority(("SCOPE_portfolio.admin")),
								AuthorityAuthorizationManager.hasRole("portfolio-admin")
						))
						.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())));
		return http.build();
	}

	private JwtAuthenticationConverter customJwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return converter;
	}






	/**
 *
 *jwt -> jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz
						.requestMatchers("/support/admin/**").access(AuthorizationManagers.allOf(
								AuthorityAuthorizationManager.hasAuthority(("SCOPE_portfolio.admin")),
								AuthorityAuthorizationManager.hasRole("portfolio-admin")
						))
				.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2
								.jwt(Customizer.withDefaults()));
					//	.jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())));
		return http.build();
	}


		protected void configure (HttpSecurity http) throws Exception {
		http.cors().and()
				.authorizeRequests()
				.mvcMatchers("/support/admin/**").access("hasRole('ADMIN') && hasAuthority('SCOPE_portfolio-service-admin')")
				.anyRequest().authenticated()
				.and()
				.oauth2ResourceServer()
				.jwt()
				.decoder(jwtTokenDecoder())
				.jwtAuthenticationConverter(new JwtGrantedAuthoritiesConverter());
		;
	}

		private JwtDecoder jwtTokenDecoder () {
		NimbusJwtDecoderJwkSupport decoder = (NimbusJwtDecoderJwkSupport)
				JwtDecoders.fromOidcIssuerLocation("http://localhost:8081/auth/realms/CryptoInc");
		OAuth2TokenValidator<Jwt> defaultValidators = JwtValidators
				.createDefaultWithIssuer("http://localhost:8081/auth/realms/CryptoInc");
		OAuth2TokenValidator<Jwt> delegatingValidator =
				new DelegatingOAuth2TokenValidator<>(defaultValidators, new CryptoJwtTokenValidator());
		decoder.setJwtValidator(delegatingValidator);
		return decoder;
	}

	**/
}
