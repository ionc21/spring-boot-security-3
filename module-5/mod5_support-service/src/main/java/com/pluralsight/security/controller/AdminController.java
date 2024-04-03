package com.pluralsight.security.controller;

import com.pluralsight.security.model.SupportQueryResponse;
import com.pluralsight.security.service.SupportQueryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
	
	private final SupportQueryService supportQueryService;

	public AdminController(SupportQueryService supportQueryService) {
		this.supportQueryService = supportQueryService;
	}

	@GetMapping("/support/admin")
	@PreAuthorize("hasRole('portfolio-admin') && hasAuthority('SCOPE_portfolio.admin')")
	public List<SupportQueryResponse> getSupportQueries() {
		return supportQueryService.getSupportQueriesForAllUsers();
	}
		
}
