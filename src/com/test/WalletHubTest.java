/**
 * 
 */
package com.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import com.dataProvider.WalletDP;
import com.generic.*;
import com.pageObject.*;

/**
 * @author omprakash
 *
 */
public class WalletHubTest {
	public WebDriver driver;
	public Asserts asserts;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({"browser"})
	public void setUp(@Optional("chrome") String browser) {
		Selenium WHSelenium = new Selenium(browser);
		driver=WHSelenium.getDriver();
		asserts = new Asserts(WalletHubTest.class);
		
	}
	@Test(dataProviderClass = WalletDP.class, dataProvider = "WalletHubData",alwaysRun=true)
	public void walletHubSignUp(WalletDP walletHubData) throws InterruptedException{
		WalletPO walletPageObject = new WalletPO(driver);
		walletPageObject.launchUrl(walletHubData.getWalletHubSignUpURL());
		walletPageObject.enterEmail(walletHubData.getSignUpEmail());
		walletPageObject.enterPassword1(walletHubData.getSignUpPassword());
		walletPageObject.enterPassword2(walletHubData.getSignUpPassword());
		walletPageObject.unCheckCreditScore();
		walletPageObject.cliCkOnJoin();
		boolean regStatus=walletPageObject.waitForTestStatus("Thank you for registering");
		asserts.assertTrue(regStatus, "wallet hug sign up was not success");
	}
	@Test(dataProviderClass = WalletDP.class, dataProvider = "WalletHubData",dependsOnMethods = {"walletHubSignUp"})
	public void reviewSubmit(WalletDP walletHubData) throws InterruptedException{
		WalletPO walletPageObject = new WalletPO(driver);
		walletPageObject.launchUrl(walletHubData.getInsuranceUrl());
		walletPageObject.clickOnLogin();
		walletPageObject.enterEmail(walletHubData.getSignUpEmail());
		walletPageObject.enterPassword(walletHubData.getSignUpPassword());
		walletPageObject.clickOnLogin();
		walletPageObject.launchUrl(walletHubData.getInsuranceUrl());
		walletPageObject.waitForTestStatus("Your Rating");
		walletPageObject.hoverOnStarAndClick(3);
		String ActualRating = walletPageObject.getRatingValue();
		asserts.assertEquals(Integer.parseInt(ActualRating), 4, "Rattings are not entered correctly");
		walletPageObject.selectPolicy(walletHubData.getReviewOption());
		walletPageObject.enterReview(walletHubData.getReviewText());
		walletPageObject.clickOnSubmit();
		walletPageObject.launchUrl(walletHubData.getInsuranceUrl());
		boolean reviewStatus = walletPageObject.waitForTestStatus(walletHubData.getReviewText());
		asserts.assertTrue(reviewStatus, "Review not submitted sucessfully");
		
	}
	
	@AfterClass(alwaysRun = true)
	public void clear(){
		driver.close();
	}

}
