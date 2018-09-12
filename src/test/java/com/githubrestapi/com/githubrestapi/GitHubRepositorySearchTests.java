package com.githubrestapi.com.githubrestapi;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GitHubRepositorySearchTests {
	
	@BeforeClass
	public void setup() {
		RestUtil.setBaseURI("https://api.github.com/search");
		RestUtil.setBasePath("/repositories");		
	}
	
	@Test
	public void verifyGitHubRepositorySearchAPIforUserMozilla() {
		RestUtil.setContentType(ContentType.JSON);
		RestUtil.createSearchQuery("", "mozilla");
		Response res = RestUtil.getResponse();
		
		Assert.assertTrue(Helper.checkStatusIs200(res));
	}
	
	@Test
	public void verifyGitHubRepositorySearchAPIforInvalidUser() {
		RestUtil.setContentType(ContentType.JSON);
		RestUtil.createSearchQuery("", "1111abc4444");
		Response res = RestUtil.getResponse();
		
		Assert.assertTrue(Helper.checkInvalidStatus(res));
	}

	@Test
	public void verifyGitHubRepositorySearchAPIforPagination() {
		RestUtil.setContentType(ContentType.JSON);
		RestUtil.createSearchQuery("", "mozilla");
		Response res = RestUtil.getResponse();
		
		Assert.assertTrue(Helper.checkStatusIs200(res));
		String pageNo = Helper.getPageNoFromHeaderLinks(res);
		Assert.assertEquals(pageNo, "2");
		
		RestUtil.addPagetoPath(pageNo);
		Response res1 = RestUtil.getResponse();

		Assert.assertTrue(Helper.checkStatusIs200(res1));
		String pageNo1 = Helper.getPageNoFromHeaderLinks(res1);
		Assert.assertEquals(pageNo1, "3");
	}
	
	@AfterClass
	public void tearDown() {
		RestUtil.resetBaseURI();
		RestUtil.resetBasePath();
	}	
}
