package com.pageObject;

import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.generic.WebDriverUtil;

public class WalletPO extends WebDriverUtil{

	private WebDriver driver;

	public WalletPO(WebDriver driver){
		super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
	}
	
	@FindBy(css="input[name='em']")
	public static WebElement signUpEmail;
	
	@FindBy(css="input[name='pw1']")
	public static WebElement password1;
	
	@FindBy(css="input[name='pw']")
	public static WebElement loginPassword;
	
	@FindBy(css="input[name='pw2']")
	public static WebElement password2;
	
	@FindBy(css="input[type='checkbox']")
	public static WebElement freeCreditScoreCheckBox;
	
	@FindBy(css=".check")
	public static WebElement CreditScoreCheckBox;
	
	@FindBy(tagName = "button")
	public static WebElement joinButton;
	
	@FindBy(xpath = ".//span[contains(text(),'Login')]")
	public static WebElement loginButton;
	
	@FindAll({ @FindBy(xpath = ".//div[contains(@class,'review-action')]//div/*[contains(@class,'rvs-star-svg')]") })
	public static List<WebElement> ratingStarts;
	
	@FindBy(css = "write-review>review-star")
	public static WebElement reviewStar;
	
	@FindAll({@FindBy(css = ".wrev-user-input-box .dropdown-list .dropdown-item")})
	public static List<WebElement> policyDropDownList;
	
	@FindBy(css = ".wrev-user-input-box .dropdown")
	public static WebElement policyDropDown;
	
	@FindBy(css = ".wrev-user-input-box textarea")
	public static WebElement reviewTextArea;
	
	@FindBy(css = ".sbn-action")
	public static WebElement sbumitButton;
	
	public WalletPO launchUrl(String url){
		get(url);
		return this;
	}
	
	public WalletPO enterEmail(String emailAddress){
		isElementExist(signUpEmail);
		sendKeys(signUpEmail, emailAddress);
		return this;
	}
	
	public WalletPO enterPassword1(String password){
		sendKeys(password1, password);
		return this;
	}
	
	public WalletPO enterPassword2(String password){
		sendKeys(password2, password);
		return this;
	}
	
	public WalletPO unCheckCreditScore() {
		System.out.println(ischecked(freeCreditScoreCheckBox));
		if(ischecked(freeCreditScoreCheckBox)) {
			click(CreditScoreCheckBox);
		}
		return this;
	}
	public WalletPO cliCkOnJoin(){
		click(joinButton);
		return this;
	}
	
	public boolean waitForTestStatus(String newsText) {
		return waitTextPresent(newsText,30);
	}
	
	public WalletPO clickOnLogin(){
		click(loginButton);
		return this;
	}
	public WalletPO enterPassword(String password){
		sendKeys(loginPassword, password);
		return this;
	}
	public WalletPO hoverOnStarAndClick(int number){
		int i=0;
		for(WebElement e : ratingStarts) 
        { 
			if(i==number) {
				hoverOverElement(e);
				click(e);
				break;
			}
		i++;
        }
		return this;		
	}
	
	public String getRatingValue() {
		return getAttibute(reviewStar, "ng-reflect-rating");
	}
	
	public WalletPO selectPolicy(String textValue) {
		click(policyDropDown);
		wait(1);
		for(WebElement e : policyDropDownList) 
        { 
			if(getText(e).trim().equals(textValue.trim())) {
				click(e);
				break;
			}
        }
		return this;
	}
	
	public WalletPO clickOnSubmit(){
		click(sbumitButton);
		wait(4);
		return this;
	}
	
	public WalletPO enterReview(String reviewText) {
		sendKeys(reviewTextArea, reviewText);
		return this;
	}

}
