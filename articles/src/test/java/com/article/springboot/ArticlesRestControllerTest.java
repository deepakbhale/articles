package com.article.springboot;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.article.springboot.model.Article;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")

public class ArticlesRestControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	@Order(1) 
	public void saveArticleOK() throws Exception {
		String expected = "{\r\n" + "  \"id\": 1,\r\n" + "  \"title\": \"Article 1\",\r\n"
				+ "  \"body\" : \"test body 1\",\r\n" + "  \"tags\" : [\"health\",\"science\",\"fitness\"]\r\n" + "}";

		Set<String> set = new HashSet<>();

		set.add("health");
		set.add("science");
		set.add("fitness");
		Article article = new Article();

		article.setTitle("Article 1");
		article.setBody("test body 1");
		article.setTags(set);

		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/article/", article, String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

	}
	
	@Test
	@Order(2) 
	public void find_Article_OK() throws JSONException {

		String expected = "{\r\n" + "  \"title\": \"Article 1\",\r\n" + "  \"body\" : \"test body 1\",\r\n"
				+ "  \"tags\" : [\"health\",\"science\",\"fitness\"]\r\n" + "}";

		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/article/1", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	@Order(3) 
	public void find_article_NOT_FOUND() throws JSONException {

		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/article/10", String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}
	
	@Test
	@Order(4) 
	public void find_Article_Tag_Date_OK() throws JSONException {

		String expected = "{\r\n" + 
				"    \"tag\": \"health\",\r\n" + 
				"    \"count\": 1,\r\n" + 
				"    \"articles\": [\r\n" + 
				"        \"1\"\r\n" + 
				"    ],\r\n" + 
				"    \"related_tags\": [\r\n" + 
				"        \"fitness\",\r\n" + 
				"        \"science\"\r\n" + 
				"    ]\r\n" + 
				"}";
	
		Date todaysDate = new Date();
				DateFormat df = new SimpleDateFormat("yyyyMMdd");
				String strDate = df.format(todaysDate);
				String endpoint ="/api/v1/tags/health/"+strDate;
				
				
		ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}
	@Test
	@Order(5) 
	public void find_Article_Tag_Date_oldDate() throws JSONException {

	
		Date todaysDate = new Date(1);
		
				DateFormat df = new SimpleDateFormat("yyyyMMdd");
				String strDate = df.format(todaysDate);
				String endpoint ="/api/v1/tags/health/"+strDate;
				
				
		ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		
	}

	@Test
	@Order(6) 
	public void find_Article_Tag_Date_unknownTag() throws JSONException {

	
		Date todaysDate = new Date();
		
				DateFormat df = new SimpleDateFormat("yyyyMMdd");
				String strDate = df.format(todaysDate);
				String endpoint ="/api/v1/tags/random/"+strDate;
				
				
		ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		
	}
	
}
