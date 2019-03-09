package com.ty.executor;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestCaseExecutor {
	

	public  void run(String testCaseName) throws InterruptedException {
		
		
		ExcelUtil eu = new ExcelUtil(Driver.filePath);
//		String testCaseName = "DeleteUser";
		int rowCount = eu.getRowCount(testCaseName);
		for (int i = 1; i <= rowCount; i++) {
			String action = (String) eu.getCellData(testCaseName, i, 0);
			String idMethod = null, locator = null, data = null, status = null;
			if (!action.equalsIgnoreCase("verifytitle") && !action.startsWith("alert")) {
				idMethod = (String) eu.getCellData(testCaseName, i, 1);
				locator = (String) eu.getCellData(testCaseName, i, 2);
			}

			if (!action.startsWith("click") && (!action.startsWith("alert") || action.equalsIgnoreCase("alertverifytext"))) {
				data = (String) eu.getCellData(testCaseName, i, 3);
			}
			
			switch (action.toLowerCase()) {
			case "type":
				status = type(idMethod, locator, data);
				break;
			case "click":
				status = click(idMethod, locator);
				break;
			case "clickwait":
				status = click(idMethod, locator);
				Thread.sleep(3000);
				break;
			case "verifytitle":
				status = verifytitle(data);
				break;
			case "verifytext":
				status = verifyText(idMethod, locator, data);
				break;
			case "alertverifytext":
				status = alert(action,data);
				Thread.sleep(2000);
				break;
			default:
					if(action.toLowerCase().startsWith("alert"))
						status = alert(action);
					Thread.sleep(2000);
				break;
			}
			eu.writeToCell(testCaseName, i, 4, status);
		}

		

	}

	private  String alert(String action, String data) {
		String status = null;
		try {
			Alert al = Driver.driver.switchTo().alert();
			String actualAlertText = al.getText();
			if (actualAlertText.contains(data))
				status = "pass";
			else
				status = "FAIL";
		} catch (Exception e) {
			status = "FAIL: " + e.getMessage();
		}
		return status;
	}

	private  String alert(String action) {
		String status = null;
		try {
			Alert al =  Driver.driver.switchTo().alert();
			if(action.toLowerCase().contains("accept"))
				al.accept();
			else
				al.dismiss();
			status = "PASS";
		} catch (Exception e) {
			status = "FAIL: " + e.getMessage();
		}
		return status;
		
	}

	private  String verifyText(String idMethod, String locator, String data) {
		String status = null;
		try {
			WebElement element = getElement(idMethod, locator);
			String actualText = element.getText();
			if (actualText.contains(data)) {
				status = "PASS";
			} else {
				status = "FAIL";
			}
		} catch (Exception e) {
			status = "FAIL: " + e.getMessage();
		}
		return status;
	}

	private  String verifytitle(String data) {
		String status = null;
		try {
			String actualTitle =  Driver.driver.getTitle();
			if (actualTitle.contains(data))
				status = "pass";
			else
				status = "FAIL";
		} catch (Exception e) {
			status = "FAIL: " + e.getMessage();
		}
		return status;
	}

	private  String click(String idMethod, String locator) {
		String status = null;
		try {
			WebElement element = getElement(idMethod, locator);
			element.click();
			status = "PASS";
		} catch (Exception e) {
			status = "FAIL: " + e.getMessage();
		}
		return status;
	}

	private  String type(String idMethod, String locator, String data) {
		String status = null;
		try {
			WebElement element = getElement(idMethod, locator);
			element.sendKeys(data);
			status = "PASS";
		} catch (Exception e) {
			status = "FAIL: " + e.getMessage();
		}
		return status;
	}

	private  WebElement getElement(String idMethod, String locator) throws Exception {
		WebElement element = null;
		switch (idMethod.toLowerCase()) {
		case "id":
			element =  Driver.driver.findElement(By.id(locator));
			break;
		case "name":
			element =  Driver.driver.findElement(By.name(locator));
			break;
		case "classname":
			element =  Driver.driver.findElement(By.className(locator));
			break;
		case "linktext":
			element =  Driver.driver.findElement(By.linkText(locator));
			break;
		case "partiallinktext":
			element =  Driver.driver.findElement(By.partialLinkText(locator));
			break;
		case "xpath":
			element =  Driver.driver.findElement(By.xpath(locator));
			break;
		case "cssselector":
			element =  Driver.driver.findElement(By.cssSelector(locator));
			break;
		case "tagname":
			element =  Driver.driver.findElement(By.tagName(locator));
			break;
		default:
			throw new Exception("Invalid Id Method " + idMethod);
		}
		return element;
	}

}
