package com.pluralsight.security.model;

import java.util.Calendar;
import java.util.List;

public class SupportQueryResponse {

	private final Long id;
	private final String subject;
	private final Calendar creationTime;
	private final String username;
	private final boolean resolved;
	private final List<PostResponse> posts;

	public SupportQueryResponse(Long id, String subject, Calendar creationTime, String username, boolean resolved, List<PostResponse> posts) {
		this.id = id;
		this.subject = subject;
		this.creationTime = creationTime;
		this.username = username;
		this.resolved = resolved;
		this.posts = posts;
	}

	public Long getId() {
		return this.id;
	}

	public String getSubject() {
		return this.subject;
	}

	public Calendar getCreationTime() {
		return this.creationTime;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isResolved() {
		return this.resolved;
	}

	public List<PostResponse> getPosts() {
		return this.posts;
	}

	public String toString() {
		return "SupportQueryResponse(id=" + this.getId() + ", subject=" + this.getSubject() + ", creationTime=" + this.getCreationTime() + ", username=" + this.getUsername() + ", resolved=" + this.isResolved() + ", posts=" + this.getPosts() + ")";
	}
}
