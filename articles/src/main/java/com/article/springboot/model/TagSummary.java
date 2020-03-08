package com.article.springboot.model;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class TagSummary {

	private static final AtomicLong counter = new AtomicLong();

	private String tag;
	private long count;
	private Set<String> articles = new HashSet<>();
	private Set<String> related_tags = new HashSet<>();

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String gettag() {
		return tag;
	}

	public void settag(String tag) {
		this.tag = tag;
	}

	public Set<String> getArticles() {
		return articles;
	}

	public void setArticles(String articleId) {
		this.articles.add(articleId);
	}

	public Set<String> getRelated_tags() {
		return related_tags;
	}

	public void setRelated_tags(Set<String> related_tags) {
		this.related_tags.addAll(related_tags);
	}

	public static AtomicLong getCounter() {
		return counter;
	}

	public TagSummary(long count, String tag, Set<String> articles, Set<String> related_tags) {
		super();
		this.count = count;
		this.tag = tag;
		this.articles = articles;
		this.related_tags = related_tags;
	}

	public TagSummary() {
		// TODO Auto-generated constructor stub
	}

}
