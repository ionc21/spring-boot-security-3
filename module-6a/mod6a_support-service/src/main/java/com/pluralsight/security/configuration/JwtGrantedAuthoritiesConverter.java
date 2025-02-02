package com.pluralsight.security.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
		List<GrantedAuthority> roles = ((List<String>)realmAccess.get("roles")).stream()
				.map(roleName -> "ROLE_" + roleName) // prefix to map to a Spring Security "role"
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		List<GrantedAuthority> scopes = Arrays.stream(jwt.getClaimAsString("scope").split(" ")).toList().stream().map(scopeName -> "SCOPE_" + scopeName)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		List<GrantedAuthority> authorities = new ArrayList<>(roles);
		authorities.addAll(scopes);
		return authorities;
	}

}