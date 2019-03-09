package com.ty.executor;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Driver {
	public static WebDriver driver;
	@BeforeClass
	public void openBrowser() {
		filePath = JOptionPane.showInputDialog("Enter File Path");
		if(filePath !=null) {
			ExcelUtil eu = new ExcelUtil(filePath);
			String browserType = (String) eu.getCellData("DriverDetails", 0, 1);
			if (browserType.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", "exe Files/IEDriverServer.exe");
				 driver = new InternetExplorerDriver();
			} else if (browserType.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "exe Files/geckodriver.exe");
				 driver = new FirefoxDriver();
			} else if (browserType.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "exe Files/chromedriver.exe");
				 driver = new ChromeDriver();
			}
			driver.manage().window().maximize();
			String url = (String) eu.getCellData("DriverDetails", 1, 1);
			driver.get(url);
		}
		
	}

	@AfterClass
	public void closeBrowser() {
		driver.close();
	}
//	public static String filePath = "H:\\TY Class Room Programs\\Morning Batch\\ActitimeTestCases.xlsx";
	public static String filePath;
	@DataProvider
	public Object[][] getTestCaseNames() {
//		Object[][] data = new Object[3][1];
//		data[0][0] = "LoginTestCase";
//		data[1][0] = "CreateUser";
//		data[2][0] = "DeleteUser";
		
		ExcelUtil eu = new ExcelUtil(filePath);
		String testScenarioList = "ScenariosList";
		int rowCount = eu.getRowCount(testScenarioList);
		ArrayList<String> tempScenarioList = new ArrayList<String>();
		for(int i = 1;i<=rowCount;i++) {
			String exeStatus = (String) eu.getCellData(testScenarioList, i, 1);
			if(exeStatus.equalsIgnoreCase("yes")) {
				String testCaseName = (String) eu.getCellData(testScenarioList, i, 0);
				tempScenarioList.add(testCaseName);
			}
		}
		Object[][] data = new Object[tempScenarioList.size()][1];
		for(int i = 0;i<tempScenarioList.size();i++) {
			data[i][0] = tempScenarioList.get(i);
		}
		return data;
	}

	@Test(dataProvider = "getTestCaseNames")
	public void testCases(String testCase) throws InterruptedException {
		TestCaseExecutor te = new TestCaseExecutor();
		te.run(testCase);
	}

}
