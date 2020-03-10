
## Overview
REST API for articles

## Requirement
The first endpoint, POST /articles should handle the receipt of some article data in json format, and store it within the service.

The second endpoint GET /articles/{id} should return the JSON representation of the article.

The final endpoint, GET /tags/{tagName}/{date} will return the list of articles that have that tag name on the given date and some summary data about that tag for that day.

An article has the following attributes id, title, date, body, and list of tags. for example:

{
  "id": "1",
  "title": "latest science shows that potato chips are better for you than sugar",
  "date" : "2016-09-22",
  "body" : "some text, potentially containing simple markup about how potato chips are great",
  "tags" : ["health", "fitness", "science"]
}

The GET /tag/{tagName}/{date} endpoint should produce the following JSON. Note that the actual url would look like /tags/health/20160922.

{
  "tag" : "health",
  "count" : 17,
    "articles" :
      [
        "1",
        "7"
      ],
    "related_tags" :
      [
        "science",
        "fitness"
      ]
}
The related_tags field contains a list of tags that are on the articles that the current tag is on for the same day. It should not contain duplicates.

The count field shows the number of tags for the tag for that day.

The articles field contains a list of ids for the last 10 articles entered for that day.

## Design

please refer swagger.yaml for Low evel Design

## Postman test cases

To connect to API deployed in AWS pl refer AWS-ArtcileAPITest.postman_collection

## Assumption

Not connected to database instead  added and fetched dta from map within ArticlesServiceImpl.java

## Prerequisites
- Java 8
- Maven 3
- Eclipse IDE for Java Developers Version: 2019-12 (4.14.0)


## Installation 

Checkout the folder from gitHub and import it as Maven project
Right click on project->maven->update Project 
Set Maven build goal spring-boot:run and execute it.

I have tested using Postman as well as unit test cases



## Build
`mvn clean install`

## Test
`mvn clean test`

## Version
v1
