package com.githubrestapi.com.githubrestapi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.net.MalformedURLException;
import java.net.URL;

public class RestUtil {

	// path will be used to add search query to url 
	public static String path;
	public static String baseURI;
	public static String basePath;
	
	public static void setBaseURI(String bURI) {
		baseURI = bURI;
	}
	
	public static void resetBaseURI() {
		baseURI = null;
	}
	
	public static void setBasePath(String bPath) {
		basePath = bPath;
	}
	
	public static void resetBasePath() {
		basePath = null;
	}
	
	public static void setContentType(ContentType type) {
		given().contentType(type);
	}
	
	public static void createSearchQuery(String searchTerm, String user) {
		if(searchTerm.isEmpty()) {
			path = "?q=" + "user:" + user;
		}
		else {
			path = "?q=" + searchTerm + "+" + "user:" + user;			
		}
	}
	
	public static void addPagetoPath(String pageNo) {
		path = path + "&page=" + pageNo;
	}
	
	public static Response getResponse() {
			return get(baseURI+basePath+path);
	}
	
	public static JsonPath getJsonPath(Response resp) {
		return new JsonPath(resp.asString());
	}
	
}
