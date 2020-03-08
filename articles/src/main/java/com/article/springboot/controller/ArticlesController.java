package com.article.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.article.springboot.model.Article;
import com.article.springboot.model.TagSummary;
import com.article.springboot.service.ArticleService;
import com.article.springboot.util.CustomErrorType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/")
public class ArticlesController {

	public static final Logger logger = LoggerFactory.getLogger(ArticlesController.class);

	@Autowired
	ArticleService articleService;

	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleById(@PathVariable("id") long id) {
		logger.info("Fetching article with id {}", id);
		Article article = articleService.findById(id);
		if (article == null) {
			logger.error("article with id {} not found.", id);
			return new ResponseEntity<>(new CustomErrorType("article with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(article, HttpStatus.OK);
	}

	@RequestMapping(value = "/tags/{tagname}/{date}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleByTagNameAndId(@PathVariable("tagname") String tagname,
			@PathVariable("date") String date) {
		logger.info("Fetching article with tagname {} and {date }", tagname, date);

		TagSummary tagSummary = articleService.findByTagAndDate(tagname, date);

		if (tagSummary == null) {
			logger.error("article with tagname {}  date {} not found.", tagname, date);
			return new ResponseEntity<>(
					new CustomErrorType("article with tagname " + tagname + "    date " + date + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(tagSummary, HttpStatus.OK);
	}

	@RequestMapping(value = "/article/", method = RequestMethod.POST)
	public ResponseEntity<?> createArticle(@RequestBody Article article) {
		logger.info("Creating Product : {}", article);

		if (articleService.isArticleExist(article)) {
			logger.error("Unable to create. A Product with name {} already exist", article.getTitle());
			Article.hackCounter();
			return new ResponseEntity<>(
					new CustomErrorType(
							"Unable to create. A Product with name " + article.getTitle() + " already exist."),
					HttpStatus.CONFLICT);
		}
		Date todaysDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		article.setDate(df.format(todaysDate));
		articleService.saveArticle(article);

		return new ResponseEntity<>(article, HttpStatus.CREATED);
	}

}