package me.hossain.ebrahim.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import me.hossain.ebrahim.basedriver.BaseDriver;
import me.hossain.ebrahim.pages.LoginPage;
import me.hossain.ebrahim.pages.LoginPageNegativeTests;
import me.hossain.ebrahim.utilities.ExtentFactory;

public class LoginTest extends BaseDriver {
	
	ExtentReports report;
	ExtentTest parentTest;
	ExtentTest childTest;

	@BeforeClass
	public void openUrl(@Optional String url, @Optional String browserName, @Optional String headless) throws InterruptedException {
		Properties properties = new Properties();
		String env = System.getProperty("env", "dev");
		String configFileName = String.format("config-%s.properties", env);

		try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
			if (input == null) {
				System.out.println("Sorry, unable to find " + configFileName);
				return;
			}
			properties.load(input);
			url = url != null ? url : properties.getProperty("url");
			browserName = browserName != null ? browserName : properties.getProperty("browserName");
			headless = headless != null ? headless : properties.getProperty("headless");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		report = ExtentFactory.getInstance();
		parentTest = report.createTest("<p style=\"color:#FF6000; font-size:20px\"><b>SALES PORTAL - BOOKER</b></p>")
				.assignAuthor("QA TEAM").assignDevice("Windows");
		launchPlaywright(browserName, headless);
		launchApplication(url);
	}

	// Test case: valid Username and Password
	@Test(priority = 0)
	public void loginTest() throws IOException, InterruptedException {
		childTest = parentTest.createNode("<p style=\"color:#3E96E7; font-size:20px\"><b>Login</b></p>");
		LoginPage loginPage = new LoginPage(page, childTest);
		loginPage.login();		
	}
	
    // Test case: Invalid Username
    @Test(priority = 1)
    public void testInvalidUsername() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Invalid Username</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithInvalidUsername();
    }

    // Test case: Invalid Password
    @Test(priority = 2)
    public void testInvalidPassword() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Invalid Password</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithInvalidPassword();
    }

    // Test case: Empty Username and Password
    @Test(priority = 3)
    public void testEmptyUsernameAndPassword() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Empty Username and Password</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithEmptyUsernameAndPassword();
    }

    // Test case: Empty Username
    @Test(priority = 4)
    public void testEmptyUsername() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Empty Username</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithEmptyUsername();
    }

    // Test case: Empty Password
    @Test(priority = 5)
    public void testEmptyPassword() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Empty Password</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithEmptyPassword();
    }

    // Test case: SQL Injection Attempt
    @Test(priority = 6)
    public void testSQLInjection() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>SQL Injection</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithSQLInjection();
    }

    // Test case: Special Characters in Password
    @Test(priority = 7)
    public void testSpecialCharactersInPassword() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Special Characters in Password</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithSpecialCharactersInPassword();
    }

    // Test case: Invalid Username Format
    @Test(priority = 8)
    public void testInvalidUsernameFormat() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Invalid Username Format</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithInvalidUsernameFormat();
    }

    // Test case: Username Exceeding Maximum Length
    @Test(priority = 9)
    public void testLongUsername() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Username Exceeding Maximum Length</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithLongUsername();
    }

    // Test case: Locked Account
    @Test(priority = 10)
    public void testLockedAccount() throws InterruptedException {
        childTest = parentTest.createNode("<p style=\"color:#FF5353; font-size:18px\"><b>Locked Account</b></p>");
        LoginPageNegativeTests loginPage = new LoginPageNegativeTests(page, childTest);
        loginPage.loginWithLockedAccount();
    }
	
	@AfterClass
	public void afterClass() {
		closePlaywright();
		report.flush();
	}
}
