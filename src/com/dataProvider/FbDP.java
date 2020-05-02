package com.dataProvider;
import java.io.File;
import java.util.Properties;

import org.testng.annotations.DataProvider;

import com.generic.*;
/**
 * @author omprakash
 *
 */

public class FbDP {
	
	private String facebookURL;
	private String facebookUser;
	private String facebookPassword;
	private String fbNews;
	

	static Properties props = ToolUtil.openPropertyFile(new File(System.getProperty("user.dir")+File.separator+"config.properties"));
	
	public String getFacebookURL() {
		return facebookURL;
	}
	public void setFacebookURL(String facebookURL) {
		this.facebookURL = facebookURL;
	}
	public String getFacebookPassword() {
		return facebookPassword;
	}
	public void setFacebookPassword(String facebookPassword) {
		this.facebookPassword = facebookPassword;
	}
	public String getFacebookUser() {
		return facebookUser;
	}
	public void setFacebookUser(String facebookUser) {
		this.facebookUser = facebookUser;
	}
	public String getFbNews() {
		return fbNews;
	}
	public void setFbNews(String fbNews) {
		this.fbNews = fbNews;
	}
	

	
	@DataProvider(name = "FacebookLoginData")
	public static Object[][] getFacebookLoginData() {
		FbDP logindata = new FbDP();
		logindata.setFacebookURL(props.getProperty("facebookURL"));
		logindata.setFacebookUser(props.getProperty("facebookUser"));
		logindata.setFacebookPassword(props.getProperty("facebookPassword"));
		logindata.setFbNews(props.getProperty("fbNews"));
		
		return new FbDP[][] { { logindata } };
	}
}