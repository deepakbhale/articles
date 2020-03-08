package com.article.springboot.service;

import com.article.springboot.model.Article;
import com.article.springboot.model.TagSummary;

public interface ArticleService {

	Article findById(long id);

	void saveArticle(Article article);

	boolean isArticleExist(Article article);

	public Article findByName(String name);

	public TagSummary findByTagAndDate(String tag, String date);

}
