package com.pluralsight.security.userdetails;

import com.pluralsight.security.service.PortfolioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("oauth2authSuccessHandler")
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private final PortfolioService portfolioService;
	private final RedirectStrategy redirectStrategy;

	public Oauth2AuthenticationSuccessHandler(PortfolioService portfolioService, @Lazy  RedirectStrategy redirectStrategy) {
		this.portfolioService = portfolioService;
		this.redirectStrategy = redirectStrategy;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		if(!this.portfolioService.userHasPortfolio()) {
			this.portfolioService.createPortfolio();
		}
		this.redirectStrategy.sendRedirect(request, response, "/portfolio");
	}
	
}
