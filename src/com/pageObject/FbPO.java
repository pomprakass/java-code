package com.pageObject;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.generic.WebDriverUtil;


public class  FbPO extends WebDriverUtil{

	private WebDriver driver;

	public FbPO(WebDriver driver){
		super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
	}

	@FindBy(id="email")
	public static WebElement username;
	
	@FindBy(id="pass")
	private static WebElement password;
	
	@FindBy(id="loginbutton")
	public static WebElement login;
	
	@FindBy(id="globalContainer")	
	public static WebElement fbPage;
	
	@FindBy(xpath = ".//*[contains(@title,'Write something here')]")
	public static WebElement statusTextFiled;
	
	@FindBy(xpath=".//button[contains(@type,'submit')]//span[contains(text(),'Post')]")
	public static WebElement postButton;
	
	@FindBy(tagName="p")
	public static WebElement newsTag;
	
	public FbPO launchUrl(String url){
		get(url);
		return this;
	}
	public FbPO enterUsername(String enterUsername){
		isElementExist(username);
		sendKeys(username, enterUsername);
		return this;
	}
	
	public FbPO enterPassword(String enterPassword){
		isElementExist(password);
		sendKeys(password, enterPassword);
		return this;
	}
	
	public FbPO clickLogin() {
		isElementExist(login);
		click(login);
		return this;
		
	}
	
	public boolean fbPageStaus() {
		wait(20); //Waiting to load page
		return waitForElement(fbPage);	
	}
	
	public FbPO submitNews(String newsText) {
		isElementExist(statusTextFiled);
		click(statusTextFiled);
		sendKeys(statusTextFiled, newsText);
		wait(2); // Some times element positions will change to handle keeping wait
		click(postButton);
		return this;
	}
	
	public boolean getNewsStatus(String newsText) {
		wait(3); // waiting to submit to news feed
		return waitTextPresent(newsText,30);
	}
}
