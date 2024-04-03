package com.pluralsight.security.controller;

import com.pluralsight.security.model.SupportQueryResponse;
import com.pluralsight.security.service.SupportQueryService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupportQueryController {
		
	private final SupportQueryService supportService;

	public SupportQueryController(SupportQueryService supportService) {
		this.supportService = supportService;
	}

	@GetMapping("/support/{userId}")
	@PreAuthorize("(hasRole('portfolio-admin') && hasAuthority('SCOPE_portfolio.admin')) || #userId == authentication.token.subject")
	public List<SupportQueryResponse> getQueries(@PathVariable String userId) {
		return supportService.getSupportQueriesForUser(userId);
	}
	
	@GetMapping("/support")
	@PostAuthorize("returnObject.username = authentication.token.subject")
	public List<SupportQueryResponse> getQueries(@AuthenticationPrincipal Jwt token) {
		return supportService.getSupportQueriesForUser(token.getSubject());
	}

	@GetMapping("/support/query/{id}")
	public SupportQueryResponse getQuery(@PathVariable String id) {
		return supportService.getSupportQueryById(id);
		
	}	
	
}