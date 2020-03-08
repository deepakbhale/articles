package com.article.springboot.model;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Article {

	private static final AtomicLong counter = new AtomicLong();

	private long id;

	private String title;

	private String date;

	private String body;

	private Set<String> tags = new HashSet<>();

	public Article() {
		this.id = counter.incrementAndGet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Article(long id, String title, String date, String body, Set<String> tags) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.body = body;
		this.tags = tags;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public static AtomicLong getCounter() {
		return counter;
	}

	public static void hackCounter() {
		counter.decrementAndGet();
	}

}
