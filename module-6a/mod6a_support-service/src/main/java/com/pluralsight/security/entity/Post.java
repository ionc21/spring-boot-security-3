package com.pluralsight.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String content;
	private long timestamp;

	public Post() {}

	public Post(String username, String content, long timestamp) {
		this.username=username;
		this.content=content;
		this.timestamp=timestamp;
	}

	public Long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getContent() {
		return this.content;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof Post)) return false;
		final Post other = (Post) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$id = this.getId();
		final Object other$id = other.getId();
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		final Object this$username = this.getUsername();
		final Object other$username = other.getUsername();
		if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
		final Object this$content = this.getContent();
		final Object other$content = other.getContent();
		if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
		if (this.getTimestamp() != other.getTimestamp()) return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Post;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $id = this.getId();
		result = result * PRIME + ($id == null ? 43 : $id.hashCode());
		final Object $username = this.getUsername();
		result = result * PRIME + ($username == null ? 43 : $username.hashCode());
		final Object $content = this.getContent();
		result = result * PRIME + ($content == null ? 43 : $content.hashCode());
		final long $timestamp = this.getTimestamp();
		result = result * PRIME + (int) ($timestamp >>> 32 ^ $timestamp);
		return result;
	}

	public String toString() {
		return "Post(id=" + this.getId() + ", username=" + this.getUsername() + ", content=" + this.getContent() + ", timestamp=" + this.getTimestamp() + ")";
	}
}


