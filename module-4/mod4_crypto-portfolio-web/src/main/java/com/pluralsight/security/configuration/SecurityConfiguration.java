package com.pluralsight.security.configuration;

import com.pluralsight.security.service.CryptoOidcUserService;
import com.pluralsight.security.service.KeycloakLogoutHandler;
import com.pluralsight.security.userdetails.CryptoGrantedAuthoritiesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
public class SecurityConfiguration {
	

	private final AuthenticationSuccessHandler oauth2authSuccessHandler;
	private final KeycloakLogoutHandler logoutHandler;

	public SecurityConfiguration(AuthenticationSuccessHandler oauth2authSuccessHandler, KeycloakLogoutHandler logoutHandler) {
		this.oauth2authSuccessHandler = oauth2authSuccessHandler;
		this.logoutHandler = logoutHandler;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.logout(lo -> lo.addLogoutHandler(logoutHandler))
						.oauth2Login(login -> {
							login.loginPage("/oauth2/authorization/crypto-portfolio")
									.failureUrl("/login-error")
									.successHandler(oauth2authSuccessHandler)
									.userInfoEndpoint(ui -> ui.oidcUserService(new CryptoOidcUserService()));
						})
								.authorizeHttpRequests(auth -> {
									auth.requestMatchers("/login","/login-error").permitAll()
											.anyRequest().authenticated();
								}).build();
	}

	@Bean
	public GrantedAuthoritiesMapper userAuthoritiesMapper() {
		return (authorities) -> {
			final Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
			authorities.forEach((authority) -> {
				if (authority instanceof OidcUserAuthority oidcAuth) {
					mappedAuthorities.addAll(mapAuthorities(oidcAuth.getIdToken().getClaims()));
				} else if (authority instanceof OAuth2UserAuthority oauth2Auth) {
					mappedAuthorities.addAll(mapAuthorities(oauth2Auth.getAttributes()));
				}
			});
			return mappedAuthorities;
		};
	}

	/**
	 * Read claims from attribute realm_access.roles as SimpleGrantedAuthority.
	 */
	private List<SimpleGrantedAuthority> mapAuthorities(final Map<String, Object> attributes) {
		final Map<String, Object> realmAccess = ((Map<String, Object>)attributes.getOrDefault("realm_access", Collections.emptyMap()));
		final Collection<String> roles = ((Collection<String>)realmAccess.getOrDefault("roles", Collections.emptyList()));
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if(roles.contains("portfolio-admin")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css/**", "/webjars/**");
	}
	
	@Bean
	protected RedirectStrategy getRedirectStrategy() {
		return new DefaultRedirectStrategy();
	}	
	
	//@Bean
	protected GrantedAuthoritiesMapper getGrantedAuthoritiesMapper() {
		return new CryptoGrantedAuthoritiesMapper();
	}
	

}