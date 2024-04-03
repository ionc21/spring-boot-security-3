package com.pluralsight.security.controller;

import com.pluralsight.security.model.CreateSupportQueryRequest;
import com.pluralsight.security.model.PostRequest;
import com.pluralsight.security.service.SupportCommandService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupportCommandController {

	private final SupportCommandService supportService;

	public SupportCommandController(SupportCommandService supportService) {
		this.supportService = supportService;
	}

	@PutMapping("/support")
	public void createNewQuery(@RequestBody CreateSupportQueryRequest createSupportQueryRequest) {
		supportService.createQuery(createSupportQueryRequest);
	}

	@PostMapping("/support/query/{id}")
	public void postToQuery(@RequestBody PostRequest postDto, @PathVariable String id, @AuthenticationPrincipal Jwt token) {
		postDto.setQueryId(id);
		supportService.postToQuery(postDto);
		if(postDto.isResolve()) {
			this.supportService.resolveQuery(id);
		}
	}
	
}
