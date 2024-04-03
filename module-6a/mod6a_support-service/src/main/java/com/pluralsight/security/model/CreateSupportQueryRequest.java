package com.pluralsight.security.model;

public class CreateSupportQueryRequest {
	
	private String subject;
	private String content;
	private boolean resolved;
	private String username;

	public CreateSupportQueryRequest(String subject, String content, boolean resolved, String username) {
		this.subject = subject;
		this.content = content;
		this.resolved = resolved;
		this.username = username;
	}

	public CreateSupportQueryRequest() {
	}

	public String getSubject() {
		return this.subject;
	}

	public String getContent() {
		return this.content;
	}

	public boolean isResolved() {
		return this.resolved;
	}

	public String getUsername() {
		return this.username;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return "CreateSupportQueryRequest(subject=" + this.getSubject() + ", content=" + this.getContent() + ", resolved=" + this.isResolved() + ", username=" + this.getUsername() + ")";
	}
}
