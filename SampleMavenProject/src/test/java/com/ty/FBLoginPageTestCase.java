package com.ty;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Test;

public class FBLoginPageTestCase {
	WebDriver driver;

	public void openApplicationAndCloseBrowser() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com");
		String currentTitle = driver.getTitle();
		System.out.println("Current Title = " + currentTitle);
		Thread.sleep(3000);
	}

	@Test
	public void checkFBInChrome() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/exe Files/chromedriver.exe");
		driver = new ChromeDriver();
		openApplicationAndCloseBrowser();
		driver.close();
	}
	@Test
	public void checkFBInMozilla() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/exe Files/geckodriver.exe");
		driver = new FirefoxDriver();
		openApplicationAndCloseBrowser();
		driver.close();
	}
	@Test
	public void checkFBInIE() throws InterruptedException {
		System.setProperty("webdriver.ie.driver", "src/test/resources/exe Files/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		openApplicationAndCloseBrowser();
		driver.quit();
		example(null);
	}
	
	public void example(WebElement element) throws ElementNotVisibleException {
		if(element !=null && element.isDisplayed() && element.isEnabled()) {
			element.click();
		}else {
			throw new ElementNotVisibleException("The element you are trying to click is not either displayed, not enabled");
		}
	}

}
