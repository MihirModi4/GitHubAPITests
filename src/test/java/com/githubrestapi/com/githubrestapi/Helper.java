package com.githubrestapi.com.githubrestapi;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Helper {
	
	public static boolean checkStatusIs200(Response resp) {
		return resp.getStatusCode() == 200;
	}
	
	//Verify that status code is between 400 and 499 for Invalid Condition
	public static boolean checkInvalidStatus(Response resp) {
		return resp.getStatusCode() >= 400 && resp.getStatusCode() < 500;
	}
	
	
	//Header Link attribute has link url for next and last page no.
	//Extract next page number from that link
	public static String getPageNoFromHeaderLinks(Response resp) {
		if (resp.getStatusCode() != 200) {
			return "0";
		}
		JsonPath jp = RestUtil.getJsonPath(resp);
		if (jp.getInt("total_count") <= 30) {
			return "1";
		}
		String headerLink = resp.header("Link");
		String[] eachLink = headerLink.split(",");
		for(String alink : eachLink) {
			if (alink.contains("rel=\"next\"")) {
				int index = alink.indexOf("page=");
				int lindex = alink.indexOf(">;");
				String pageNo = alink.substring(index+5, lindex);
				System.out.println("Page no: '" + pageNo +"'" );
				return pageNo;
			}
		}
		return "1";		
	}
}
