package com.pluralsight.security.service;

import com.pluralsight.security.userdetails.OidcCryptoUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class CryptoOidcUserService extends OidcUserService {
	
	final OidcUserService delegate = new OidcUserService();
	
	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcCryptoUser user = new OidcCryptoUser(super.loadUser(userRequest));
		return user;
	}
	
}
