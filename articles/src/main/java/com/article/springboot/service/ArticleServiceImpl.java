package com.article.springboot.service;

import org.springframework.stereotype.Service;

import com.article.springboot.model.Article;
import com.article.springboot.model.TagSummary;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service("ArticleService")
public class ArticleServiceImpl implements ArticleService {

	// Using two hashmaps in order to provide performance of O(1) while fetching
	// articles
	private static HashMap<Long, Article> articles = new HashMap<>();
	private static HashMap<String, Long> idNameHashMap = new HashMap<>();

	public Article findById(long id) {
		return articles.get(id);
	}

	public Article findByName(String name) {

		if (idNameHashMap.get(name) != null) {
			return articles.get(idNameHashMap.get(name));
		}

		return null;
	}

	public void saveArticle(Article article) {
		synchronized (this) { // Critical section synchronized
			articles.put(article.getId(), article);
			idNameHashMap.put(article.getTitle(), article.getId());
		}
	}

	public boolean isArticleExist(Article article) {
		return findByName(article.getTitle()) != null;
	}

	public TagSummary findByTagAndDate(String tag, String date) {

		Map<Long, Article> filteredArticlesByTag = articles.entrySet().stream()
				.filter(map -> tag.equals(
						map.getValue().getTags().stream().filter(s -> s.equals(tag)).collect(Collectors.joining())))
				.filter(map -> date.equals(map.getValue().getDate()))
				.collect(Collectors.toMap((p -> p.getKey()), p -> p.getValue()));

		if (!filteredArticlesByTag.isEmpty()) {

			TagSummary summary = new TagSummary();
			summary.settag(tag);
			summary.setCount(filteredArticlesByTag.size());
			filteredArticlesByTag.forEach((k, v) -> {

				summary.setRelated_tags(v.getTags().stream().filter(s -> !s.equals(tag)).collect(Collectors.toSet()));
				summary.setArticles(String.valueOf(v.getId()));

			});

			return summary;
		}

		else {
			return null;
		}

	}

}
