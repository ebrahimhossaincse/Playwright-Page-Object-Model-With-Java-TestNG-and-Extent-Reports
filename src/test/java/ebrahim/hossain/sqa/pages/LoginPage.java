package ebrahim.hossain.sqa.pages;

import java.nio.file.Paths;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import ebrahim.hossain.sqa.utilities.CommonMethods;

public class LoginPage extends CommonMethods {
	// Define a Page object that represents the browser page in Playwright
	Page page;

	// Define an ExtentTest object for logging and reporting test results using
	// ExtentReports
	ExtentTest test;

	// Define ElementHandle objects for the username, password, and login button
	// elements
	private ElementHandle username;
	private ElementHandle password;
	private ElementHandle login_button;

	// Constructor to initialize the page and test objects, and locate the login
	// elements
	public LoginPage(Page page, ExtentTest test) {
		// Initialize the Page object from the parameter
		this.page = page;
		// Initialize the ExtentTest object from the parameter
		this.test = test;

		// Locate the username input field using an XPath selector
		this.username = page.querySelector("//input[@name='username']");
		// Locate the password input field using an XPath selector
		this.password = page.querySelector("//input[@name='password']");
		// Locate the login button using an XPath selector
		this.login_button = page.querySelector("//button[@type='submit']");
	}

	// Getter method for the username ElementHandle
	public ElementHandle getUsername() {
		return username;
	}

	// Setter method to update the username ElementHandle
	public void setUsername(ElementHandle username) {
		this.username = username;
	}

	// Getter method for the password ElementHandle
	public ElementHandle getPassword() {
		return password;
	}

	// Setter method to update the password ElementHandle
	public void setPassword(ElementHandle password) {
		this.password = password;
	}

	// Getter method for the login button ElementHandle
	public ElementHandle getLogin_button() {
		return login_button;
	}

	// Setter method to update the login button ElementHandle
	public void setLogin_button(ElementHandle login_button) {
		this.login_button = login_button;
	}

	// Method to log a success message with ExtentReports
	public void handlePass(String message) {
		// Log a message with green color indicating a pass
		test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
	}

	// Method to log a success message and capture a screenshot
	public void handlePassWithScreenshot(String message, String screenshotName) {
		// Log a message with green color and include bold formatting
		test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
		// Capture a full-page screenshot and save it to the specified path
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
				.setFullPage(true));
		// Build the full path for the screenshot file
		String dest = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
		// Attach the screenshot to the test report
		test.pass(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
	}

	// Method to log a failure message, capture a screenshot, and log an exception
	public void handleFail(String message, String screenshotName) {
		// Log a failure message with red color indicating an error
		test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
		// Create an exception and log it in the report
		Throwable t = new InterruptedException("Exception");
		test.fail(t);
		// Capture a full-page screenshot in case of failure and save it to the
		// specified path
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
				.setFullPage(true));
		// Build the full path for the screenshot file
		String dest = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
		// Attach the screenshot to the failure report
		test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
	}

	// Method to perform the login operation
	public void login() throws InterruptedException {
		// Log the start of the login process
		test.info("Login Process");
		try {
			// Check if the username field is visible
			if (username.isVisible()) {
				// Log an instruction to enter the username
				test.info("Please enter your email address.");
				// Fill the username field with the text "Admin"
				username.fill("Admin");
				// Wait for 2 seconds to simulate a delay
				Thread.sleep(2000);
				// Log the success of entering the username
				handlePass("You had successfully entered your username");

				try {
					// Check if the password field is visible
					if (password.isVisible()) {
						// Log an instruction to enter the password
						test.info("Please enter your password.");
						// Fill the password field with the text "admin123"
						password.fill("admin123");
						// Wait for 2 seconds to simulate a delay
						Thread.sleep(2000);
						// Log the success of entering the password
						handlePass("You had successfully entered your password");

						try {
							// Check if the login button is visible
							if (login_button.isVisible()) {
								// Log an instruction to click the login button
								test.info("Please click on the Login Button.");
								// Click the login button
								login_button.click();
								// Wait for 5 seconds to simulate the login process
								Thread.sleep(5000);
								// Log the success of the login attempt and capture a screenshot
								handlePassWithScreenshot("You had successfully logged in", "login_success");
							}
						} catch (Exception e) {
							// Handle failure when login button is not found and capture a screenshot
							handleFail("Login Button was not locateable. Please check the error message",
									"login_button_fail");
						}
					}
				} catch (Exception e) {
					// Handle failure when password field is not found and capture a screenshot
					handleFail("Password was not locateable. Please check the error message", "password_fail");
				}
			}
		} catch (Exception e) {
			// Handle failure when username field is not found and capture a screenshot
			handleFail("User Name was not locateable. Please check the error message", "username_fail");
		}
	}

}
