
package com.generic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This class is used to get a Selenium WebDriver and launch browser
 * 
 * @author omprakash
 */
public class Selenium {
	private WebDriver driver;
	public static final String BROWSER_ENV = "turn.browser";
	public static final String BROWSER_FIREFOX = "firefox";
	public static final String BROWSER_CHROME = "chrome";
	public static final String BROWSER_IE = "iexplorer";
	private static String OS = System.getProperty("os.name").toLowerCase();
	public Selenium(FirefoxProfile profile) {
		startBrowser(profile);
		// This will allow to wait 30 second in case Selenium cannot find an
		// element, after it will return an exception
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		maximizeWindow();
	}

	/**
	 * Gets the WebDriver.
	 * 
	 * @return the Selenium WebDriver
	 */
	public WebDriver getDriver() {
		return driver;
	}


	/**
	 * Start browser
	 * 
	 * @return the Selenium WebDriver
	 */
	public void startBrowser(FirefoxProfile profile) {

		// disable certify the certificate for https
		profile.setAssumeUntrustedCertificateIssuer(false);
		/*
		 * Firefox automatically save files to a certain directory, no saveto
		 * popup window any more
		 */
		/* 'browser.download.folderList' save file to custom location */
		profile.setPreference("browser.download.folderList", 2);
		/* set path to store the download file */
		//profile.setPreference("browser.download.dir",
		//	UIConstants.INPUT_DOWNLOAD_DIRECTORY.getAbsolutePath());
		/*
		 * Add the MIME types to save to disk without ask what to use to open
		 * the file
		 */
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/vnd.ms-excel,application/csv,text/csv");

		driver = new FirefoxDriver((Capabilities) profile);
	}

	public Selenium(String browser_env) {
		if (browser_env == null || browser_env.equals(BROWSER_FIREFOX)) {
			

			FirefoxProfile profile = new FirefoxProfile();
			// disable certify the certificate for https
			profile.setAssumeUntrustedCertificateIssuer(true);
			profile.setAcceptUntrustedCertificates(true);

			/*
			 * Firefox automatically save files to a certain directory, no
			 * saveto popup window any more
			 */
			/* 'browser.download.folderList' save file to custom location */
			profile.setPreference("browser.download.folderList", 2);
			/* set path to store the download file */
			//profile.setPreference("browser.download.dir",
			//		UIConstants.INPUT_DOWNLOAD_DIRECTORY.getAbsolutePath());
			/*
			 * Add the MIME types to save to disk without ask what to use to
			 * open the file
			 */
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/vnd.ms-excel,application/csv,text/csv");

			driver = new FirefoxDriver((Capabilities) profile);
		} else if (browser_env.equals(BROWSER_CHROME)) {
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions capabilities = new ChromeOptions();

			File file = null;
			try {
				file = File.createTempFile("chrome_usr_dir", "");
			} catch (IOException e) {
			
			}
			file.delete();
			file.mkdir();
			String[] switches = { "user-data-dir=" + file.getAbsolutePath(),
					"start-maximized", "ignore-certificate-errors","disable-infobars",
					"--disable-extensions"};
			capabilities.setCapability("chrome.switches", switches);
			capabilities.setExperimentalOption("prefs", prefs);
			
			File file1 = null;
			if(OS.indexOf("win") >= 0){
			file1 = new File("lib/chromedriver.exe");
			}
			else if(OS.indexOf("mac") >= 0){
			file1 = new File("lib/chromedriver");
			}
			
			System.setProperty("webdriver.chrome.driver",
					file1.getAbsolutePath());
			driver = new ChromeDriver(capabilities);

		} else if (browser_env.equals(BROWSER_IE)) {
			
			DesiredCapabilities browserCapabillities = DesiredCapabilities
					.internetExplorer();
			browserCapabillities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,	true);
			browserCapabillities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			File file1 = null;
			if(OS.indexOf("win") >= 0){
			file1 = new File("lib/IEDriverServer.exe");
			}
			else if(OS.indexOf("mac") >= 0){
			file1 = new File("lib/IEDriverServer");
			}
			System.setProperty("webdriver.ie.driver",
					file1.getAbsolutePath());
			driver = new InternetExplorerDriver(browserCapabillities);
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Now maximize the window
		maximizeWindow();
	}

	/**
	 * Maximize windows to the screen size.
	 */
	private void maximizeWindow() {
		
		driver.manage().window().setPosition(new Point(0, 0));
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		Dimension dim = new Dimension((int) screenSize.getWidth(),
				(int) screenSize.getHeight());
		driver.manage().window().setSize(dim);
	}

	/**
	 * 
	 * Close Selenium webDriver and so close the browser
	 * 
	 */
	public void quit() {
		driver.quit();
		driver = null;
	}

	/**
	 * 
	 * Close Selenium webDriver and so close the browser
	 * 
	 */
	public void close() {
		driver.close();
		driver = null;
	}

}
