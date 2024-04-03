package com.pluralsight.security.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class SupportQuery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String subject;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Post> posts = new ArrayList();
	private Calendar created = Calendar.getInstance();
	private boolean resolved;

	public SupportQuery() {}

	public SupportQuery(String username, String subject) {
		this.username = username;
		this.subject = subject;
	}

	public void addPost(Post post) {
		this.posts.add(post);
	}
	
	public void addPost(String post, String username) {
		posts.add(new Post(username, post, System.currentTimeMillis()));
	}
	
	public void resolve() {
		this.resolved=true;
	}
	
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public Long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getSubject() {
		return this.subject;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public Calendar getCreated() {
		return this.created;
	}

	public boolean isResolved() {
		return this.resolved;
	}

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof SupportQuery)) return false;
		final SupportQuery other = (SupportQuery) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$id = this.getId();
		final Object other$id = other.getId();
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		final Object this$username = this.getUsername();
		final Object other$username = other.getUsername();
		if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
		final Object this$subject = this.getSubject();
		final Object other$subject = other.getSubject();
		if (this$subject == null ? other$subject != null : !this$subject.equals(other$subject)) return false;
		final Object this$created = this.getCreated();
		final Object other$created = other.getCreated();
		if (this$created == null ? other$created != null : !this$created.equals(other$created)) return false;
		if (this.isResolved() != other.isResolved()) return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof SupportQuery;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $id = this.getId();
		result = result * PRIME + ($id == null ? 43 : $id.hashCode());
		final Object $username = this.getUsername();
		result = result * PRIME + ($username == null ? 43 : $username.hashCode());
		final Object $subject = this.getSubject();
		result = result * PRIME + ($subject == null ? 43 : $subject.hashCode());
		final Object $created = this.getCreated();
		result = result * PRIME + ($created == null ? 43 : $created.hashCode());
		result = result * PRIME + (this.isResolved() ? 79 : 97);
		return result;
	}

	public String toString() {
		return "SupportQuery(id=" + this.getId() + ", username=" + this.getUsername() + ", subject=" + this.getSubject() + ", posts=" + this.getPosts() + ", created=" + this.getCreated() + ", resolved=" + this.isResolved() + ")";
	}
}
