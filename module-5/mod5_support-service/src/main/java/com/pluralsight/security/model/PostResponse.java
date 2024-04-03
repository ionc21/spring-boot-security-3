package com.pluralsight.security.model;

public class PostResponse {

	private Long queryId;
	private String content;
	private String username;
	private boolean resolve;

	public PostResponse(Long queryId, String content, String username, boolean resolve) {
		this.queryId = queryId;
		this.content = content;
		this.username = username;
		this.resolve = resolve;
	}

	public PostResponse() {
	}

	public Long getQueryId() {
		return this.queryId;
	}

	public String getContent() {
		return this.content;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isResolve() {
		return this.resolve;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setResolve(boolean resolve) {
		this.resolve = resolve;
	}

	public String toString() {
		return "PostResponse(queryId=" + this.getQueryId() + ", content=" + this.getContent() + ", username=" + this.getUsername() + ", resolve=" + this.isResolve() + ")";
	}
}
