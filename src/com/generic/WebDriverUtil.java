package com.generic;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
/**
 * This Class contains all generic methods for Selenium UI testing.
 * 
 * @author omprakash
 * 
 */
public class WebDriverUtil {

	public static int implicitlyWait=30;
	private WebDriver driver = null;
	private Properties props;
	
	ArrayList<String> tabs = null;

	public WebDriverUtil(WebDriver driver) {
		this.driver = driver;	
		props = ToolUtil.openPropertyFile(new File(System.getProperty("user.dir")+File.separator+"config.properties"));
	}

	/**
	 * for catching the exception.
	 * 
	 * @param seconds
	 *            the number of seconds to wait
	 */
	public void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
		}
	}
	public boolean waitForElement(final WebElement element)
	{
		nullifyImplicitlyWait();
		
		FluentWait<WebElement> wait = new FluentWait<WebElement>(element);
		wait.withTimeout(7, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class);
		try
		{
			Function<WebElement, Boolean> fun = new Function<WebElement, Boolean>()
					{
						@Override
						public Boolean apply(WebElement element) 
						{
							if(!element.isDisplayed())
							{
								return false;
							}
							else
							{
								return true;
							}		
						}
					};
						return wait.until(fun);
		}catch (TimeoutException e)
		{
			return false;
		}
		catch (NoSuchElementException e)
		{
			return false;
		}
		finally
		{
			enableImplicitlyWait();
		}
	}
	public void nullifyImplicitlyWait()
	{
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	public void enableImplicitlyWait()
	{
		driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
	}

	public boolean waitElementPast(WebElement element, int seconds) {
		try {
			for (int sec = 0;; sec++) {
				if (sec >= seconds) {
					return false;
				}
				if (element.isDisplayed()) {
					Thread.sleep(1000);
				} else {
					return true;
				}
			}
		} catch (InterruptedException e) {
			return false;
		}
	}
	public boolean waitElementPast(WebElement by) {
		return waitElementPast(by, 60);
	}

	/**
	 * Check if a text is present. We will wait a maximum of 60 S.
	 * 
	 * @param text
	 *            the text to find
	 * @return true, if text is present
	 */
	public boolean waitTextPresent(String text) {
		return waitTextPresent(text, 60);
	}

	public boolean waitTextPresent(String text, int seconds) {
		for (int sec = 0;; sec++) {
			if (sec >= seconds) {
				break;
			}
			if (isTextPresent(text)) {
				return true;
			}
			wait(1);
		}
		return false;
	}

	public boolean waitTextPast(String text) {
		return waitTextPast(text, 60);
	}

	public boolean waitTextPast(String text, int seconds) {
		for (int sec = 0;; sec++) {
			if (sec >= seconds) {
				break;
			}
			if (!isTextPresent(text)) {
				return true;
			}
			wait(1);
		}
		return false;
	}

	public void waitElementEnabled(WebElement element) {
		try {
			for (int sec = 0;; sec++) {
				if (sec >= 60) {
					break;
				}
				if (element.isEnabled()) {
					break;
				}
				wait(1);
			}
		} catch (NoSuchElementException e) {
		}
	}
	/**
	 * Accept an alert windows
	 * 
	 * @return True if the alert is present, false otherwise
	 */
	public boolean confirmAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			// Mean that the alert is not present
			return false;
		}
		return true;
	}

	/**
	 * Checks if text is present in the UI.
	 * 
	 * @param text
	 *            the text to find
	 * @return true if the text is present
	 */
	public boolean isTextPresent(String text) {
		try {
			String source = driver.getPageSource();
			return source.contains(text);
		} catch (Exception Ex) {
			return false;
		}
	}

	/**
	 * Checks if the element is present.
	 * 
	 * @param by
	 *            the element
	 * @return Selenium WebElement
	 */
	public WebElement isElementPresent(WebElement element) {
		try {
			element.isEnabled();
			return element;
			
		} catch (NoSuchElementException e) {
			
			return null;
		}
	}

	/**
	 * This function is used to make sure element shows up in the UI
	 */
	public boolean isElementDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			
			return false;
		}
	}
	public void selectValue(WebElement element, int index) {
		try {
			if (element != null) {
				Select select = new Select(element);
				select.selectByIndex(index);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void selectValue(WebElement element, String value) {
		try {
			if (element != null) {
				Select select = new Select(element);
				select.selectByVisibleText(value);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void selectContainsText(WebElement ele, String text) {
		try {
			if (ele != null) {
				Select select = new Select(ele);
				List<WebElement> options = select.getOptions();
				for (int i = 0; i < options.size(); i++) {
					String optionText = options.get(i).getText();
					if (optionText.contains(text)) {
						select.selectByIndex(i);
						return;
						
					}
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public boolean isSelectionExist(WebElement ele, String text) {
		if (ele != null) {
			Select select = new Select(ele);
			List<WebElement> options = select.getOptions();
			for (int i = 0; i < options.size(); i++) {
				String optionText = options.get(i).getText();
				if (optionText.equals(text)) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Switch from the current windows to a new one.
	 * 
	 * @return the current window ID
	 */
	public String switchWindow() {
		String curWindow = driver.getWindowHandle();
		Set<String> handlers = driver.getWindowHandles();
		for (String handler : handlers) {
			if (!handler.equals(curWindow)) {
				driver.switchTo().window(handler);
				break;
			}
		}
		return curWindow;
	}

	/**
	 * Switch from the current windows to a new one, then close the old one.
	 * 
	 * @return Id of the new window
	 */
	public String switchWindowAndClose() {
		String curWindow = driver.getWindowHandle();
		Set<String> handlers = driver.getWindowHandles();
		for (String handler : handlers) {
			if (!handler.equals(curWindow)) {
				driver.close();
				driver.switchTo().window(handler);
				break;
			}
		}
		return curWindow;
	}

	public void rightClickElement(WebElement element) {
		if (element != null) {
			Actions builder = new Actions(driver);
			Action rightClick = builder.contextClick(element).build();
			rightClick.perform();
		}
	}

	public void doubleClickElement(WebElement element) {
		if (element != null) {
			Actions builder = new Actions(driver);
			Action doubleClick = builder.doubleClick(element).build();
			doubleClick.perform();
		}
	}

	/**
	 * Checks if the checkbox element is checked.
	 * 
	 * @param element
	 *            the checkbox to check
	 * @return true, if is checked
	 */
	public boolean ischecked(WebElement element) {
		return element.isSelected();
	}

	public void reloadCurrentPage() {
		try {
			String geoURL = driver.getCurrentUrl();
			driver.get(geoURL);
			wait(2);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void get(String url){
		
		try {
			driver.get(url);
			wait(2);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Open a new window under existing webDriver.
	 * 
	 * @return String, new window ID
	 */
	public String openNewWindow() {
		JavascriptExecutor jscript = (JavascriptExecutor) driver;
		jscript.executeScript("window.open()");
		String currentWindowID = driver.getWindowHandle();
		Set<String> handlers = driver.getWindowHandles();
		for (String handler : handlers) {
			if (!handler.equals(currentWindowID)) {
				return handler;
			}
		}
		return "";
	}


	/**
	 * Scroll down scroll bar vertically
	 * 
	 * @param verPoint
	 *            the vertical point
	 * 
	 */
	public void moveScrollBar(int verPoint) {
		((JavascriptExecutor) driver).executeScript("window.scroll(0, "
				+ verPoint + ");");
		this.wait(1);
	}

	/**
	 * Call this function to click element when click() action doesn't work in
	 * webdriver Use it in actions sub-menu click
	 */
	public void clickElement(WebElement element) {
		try {
			if (element != null) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Hover over to let invisible element shows up This function doesn't work
	 * if test cases running with muli thread
	 * 
	 * @param by
	 *            pass visible item
	 */
	public void hoverOverElement(WebElement myElement) {
		int x = myElement.getLocation().x;
		int y = myElement.getLocation().y;
		try {
			Robot robot = new Robot();
			robot.mouseMove(x, y);
			wait(1);
		} catch (Exception e) {
		}
	}
	
	/**
	 * Checks if the element is present.
	 * 
	 * @param by
	 *            the element
	 * @return boolean
	 * */

	public boolean isElementExist(WebElement element) {
		if (isElementPresent(element) != null) {
			return true;
		} else {			
			return false;
		}
	}

	/**
	 * Mouse hover on web element
	 * 
	 * @param by
	 *            : the element where hover functionality has to perform
	 */
	public void mouseHoverOnElement(WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}

	/**
	 * ' Returns difference between start date and end date
	 * 
	 * @param sDate
	 *            start date
	 * @param eDate
	 *            end date
	 * @return difference between end date and start date
	 */
	public int daysDiffBetweenDates(String sDate, String eDate) {
		int diffDays = 0;
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date startDate, endDate;
		try {
			startDate = format.parse(sDate);
			endDate = format.parse(eDate);
			long diff = endDate.getTime() - startDate.getTime();

			// Adding 1 to diffdays because start date is included in the
			// application
			diffDays = (int) (diff / (24 * 60 * 60 * 1000)) + 1;
		} catch (ParseException e) {
		}
		return diffDays;
	}
		
	/**
	 * This method used to check whether element present or not.
	 * 
	 * 
	 * @param element
	 * @return true/False
	 */
	public boolean isElementPresents(WebElement element) {
		try {
			return element.isEnabled();
		} catch (NoSuchElementException e) {
			
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method used to wait to display the element.
	 * 
	 * 
	 * @param element
	 * @throws InterruptedException
	 */
	public void waitElementDisplay(WebElement element)
			throws InterruptedException {
		for (int sec = 0;; sec++) {
			if (sec >= 60) {
				break;
			}
			if (isElementPresents(element)) {
				break;
			}
			wait(1);
		}
	}
	
	/**
	 * This method used to wait to display the element up to given time
	 * 
	 * 
	 * @param element
	 * @throws InterruptedException
	 * 
	 */
	public void waitElementDisplay(WebElement element, int time)
			throws InterruptedException {
		for (int sec = 0;; sec++) {
			if (sec >= time) {
				break;
			}
			if (isElementPresents(element)) {
				break;
			}
			wait(1);
		}
	}

	/**
	 * This method is used to select a text value from dropdown.
	 * 
	 * 
	 * @param element
	 * @param textValue
	 */
	public void dropdownselect(WebElement element, String textValue) {
		try {
			Select option = new Select(element);
			option.selectByVisibleText(textValue);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to select index from dropdown.
	 * 
	 * 
	 * @param element
	 * @param index
	 */
	public void selectDropDownIndex(WebElement element, int index) {
		try {
			Select option = new Select(element);
			option.selectByIndex(index);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to search functionality.
	 * 
	 * 
	 * @param searchItemName
	 * @param textField
	 * @param submitButton
	 */
	public void search(String searchItemName, WebElement textField,
			WebElement submitButton) {
		try {
			waitElementDisplay(textField, 60);
			textField.clear();
			textField.sendKeys(searchItemName);
			submitButton.click();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}	

	/**
	 * This method is use to switch new frame.
	 * 
	 * 
	 * @param element
	 */
	public void switchToFrame(WebElement element) {
		driver.switchTo().frame(element);
	}

	/**
	 * This method is use to check element is present or not.
	 * 
	 * @param by
	 * @return WebElement
	 * @throws InterruptedException
	 */
	public WebElement isElementPresent(WebElement element, int time)
			throws InterruptedException {

		for (int sec = 0;; sec++) {
			if (sec >= time) {
				break;
			}
			if (isElementPresents(element)) {
				return element;
			}
			wait(1);
		}
		return null;
	}

	/**
	 * This Method is use to set focus on newly opened window.
	 * 
	 * 
	 */
	public void switchToNewWindow(String windowId) {
		driver.switchTo().window(windowId);
		tabs = new ArrayList<String>(driver.getWindowHandles());
		for (String winHandle : driver.getWindowHandles())
		{
		driver.switchTo().window(winHandle);
		}
	}
	
	/**
	 * This Method is use to upload file.
	 * 
	 * 
	 * @param path
	 */
	public void fileUpload(String path) {
		StringSelection ss = new StringSelection(path);
		java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(ss, null);

		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
			robot.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
			robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
			robot.keyPress(java.awt.event.KeyEvent.VK_V);
			robot.keyRelease(java.awt.event.KeyEvent.VK_V);
			robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
			robot.delay(2000);
			robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
			robot.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
			robot.delay(1000);
		} catch (AWTException e) {
			
			e.printStackTrace();
		}
	}	

	/**
	 * This method is use to switch to main window and close previously open
	 * window.
	 * 
	 * 
	 * @param failMessage
	 */
	public void switchToMainWindow(String windowId,String failMessage) {
		try {
			driver.close();
			driver.switchTo().window(windowId);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
	/**
	 * 
	 * @return
	 * This method used to get the current window unique id
	 */
	public String getCurrentWindow(){
		String  handle= driver.getWindowHandle();
		return handle;
	}
	
	public enum ElementType {
		ID, NAME, CSSSELECTOR, XPATH, LINKTEXT, CLASSNAME, PARTIALLINKTEXT, TAGNAME ;	
	}
	
	/**
	 * 
	 * @return
	 * This method used to click on any webelements
	 */
	public void click(WebElement element ) {
		try {
			element.click();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 * This method used to type text
	 */
	public void sendKeys(WebElement element, String text) {
		try {
			element.sendKeys(text);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 * This method used to clear field
	 */
	public void clear(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
		/**
		 * 
		 * @return 
		 * @return
		 * This method used to get text of any field
		 */
		public String getText(WebElement element) {
			String text=null;
			try {
				text = element.getText();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return text;
	}
		
		/**
		 * 
		 * @return
		 * This method used to click on submit button
		 */
		public void submit(WebElement element) {
			try {
				element.submit();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	}
		
	/*	public void waitForPageLoad() {

		    Wait<WebDriver> wait = new WebDriverWait(driver, 60);
		    wait.until(new Function<WebDriver, Boolean>() {
		        public Boolean apply(WebDriver driver) {
		            return ((JavascriptExecutor) driver).executeScript(
		                    "return document.readyState"
		                ).equals("complete");
		        }
		    });
			JavascriptExecutor js=(JavascriptExecutor)driver;
			for (int i=0;i<60;i++){
			
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (js.executeScript("return document.readyState").toString().equals("complete")){
						break;
				}
				
			}
		}*/
		
		public void waitForPageLoad() {

		    Wait<WebDriver> wait = new WebDriverWait(driver, 60);
		    wait.until(new Function<WebDriver, Boolean>() {
		    	@Override
		        public Boolean apply(WebDriver driver) {		                 
		            return Boolean.valueOf(((JavascriptExecutor) driver).executeScript(
		            		"return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)"
		            		).toString());
		        }
		    });
		}
		public String getAttibute(WebElement element,String value){
			   String text = null;
			   try {
			    text = element.getAttribute(value);
			   } catch (Exception e) {			    
			    e.printStackTrace();
			   }
			   return text;
			   
			  }
}
