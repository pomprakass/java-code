/**
 * 
 */
package com.test;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


import com.dataProvider.FbDP;
import com.generic.*;
import com.pageObject.*;

/**
 * @author omprakash
 *
 */
public class FacebookTest{
	public WebDriver driver;
	public Asserts asserts;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({"browser"})
	public void setUp(@Optional("chrome") String browser) {
		Selenium FBSelenium = new Selenium(browser);
		driver=FBSelenium.getDriver();
		asserts = new Asserts(FacebookTest.class);
		
	}
	@Test(dataProviderClass = FbDP.class, dataProvider = "FacebookLoginData",alwaysRun=true)
	public void fbLogin(FbDP login) throws InterruptedException{
		FbPO loginpo = new FbPO(driver);
		loginpo.launchUrl(login.getFacebookURL());
		loginpo.enterUsername(login.getFacebookUser());
		loginpo.enterPassword(login.getFacebookPassword());
		loginpo.clickLogin();
		boolean pageStatus=loginpo.fbPageStaus();
		asserts.assertTrue(pageStatus, "Facebook page was not loaded");
		loginpo.submitNews(login.getFbNews());
		boolean newsStatus=loginpo.getNewsStatus(login.getFbNews());
		asserts.assertTrue(newsStatus, "Facebook news was not submitted");
	}
	
	@AfterClass(alwaysRun = true)
	public void clear(){
		driver.close();
	}
}	