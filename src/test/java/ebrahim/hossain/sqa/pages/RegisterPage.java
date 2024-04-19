package ebrahim.hossain.sqa.pages;

import java.io.IOException;
import java.nio.file.Paths;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ebrahim.hossain.sqa.utilities.CommonMethods;

public class RegisterPage extends CommonMethods {

	Page page;
	ExtentTest test;

	private Locator firstName;
	private Locator lastName;
	private Locator userName;
	private Locator password;
	private Locator register_button;

	public RegisterPage(ExtentTest test, Page page) {
		this.page = page;
		this.test = test;

		this.firstName = page.locator("//input[@id='firstname']");
		this.lastName = page.locator("//input[@id='lastname']");
		this.userName = page.locator("//input[@id='username']");
		this.password = page.locator("//input[@id='password']");
		this.register_button = page.locator("//input[@type='submit']");
	}

	public Locator getFirstName() {
		return firstName;
	}

	public void setFirstName(Locator firstName) {
		this.firstName = firstName;
	}

	public Locator getLastName() {
		return lastName;
	}

	public void setLastName(Locator lastName) {
		this.lastName = lastName;
	}

	public Locator getUserName() {
		return userName;
	}

	public void setUserName(Locator userName) {
		this.userName = userName;
	}

	public Locator getPassword() {
		return password;
	}

	public void setPassword(Locator password) {
		this.password = password;
	}

	public Locator getRegister_button() {
		return register_button;
	}

	public void setRegister_button(Locator register_button) {
		this.register_button = register_button;
	}

	public void handlePass(String message) {
		test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
	}

	public void handlePassWithScreenshot(String message, String screenshotName) {
		test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
				.setFullPage(true));
		String dest = System.getProperty("user.dir") + "/screenshots/" + "" + screenshotName + ".png";
		test.pass(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
	}

	public void handleFail(String message, String screenshotName) {
		test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
		Throwable t = new InterruptedException("Exception");
		test.fail(t);
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
				.setFullPage(true));
		String dest = System.getProperty("user.dir") + "/screenshots/" + "" + screenshotName + ".png";
		test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
		closePlaywright();
	}

	public void login() throws IOException {
		try {
			test.info("Registration Page");
			if (firstName.isVisible()) {
				test.info("Please Enter your First Name");
				firstName.fill("Md. Ebrahim");
				page.waitForTimeout(1000);
				handlePass("You have successfully entered your first name");
				if (lastName.isVisible()) {
					test.info("Please Enter your Last Name");
					lastName.fill("Hossain");
					page.waitForTimeout(1000);
					handlePass("You have successfully entered your last name");
					if (userName.isVisible()) {
						test.info("Please Enter your User Name");
						userName.fill("ebrahim@noemail.com");
						page.waitForTimeout(1000);
						handlePass("You have successfully entered your user name");
						if(password.isVisible()) {
							test.info("Please Enter your User Name");
							password.fill("test@123");
							page.waitForTimeout(1000);
							handlePass("You have successfully entered your password");
							if(register_button.isVisible()) {
								register_button.click();
								page.waitForTimeout(1000);
								handlePass("You have successfully clicked on the Register Button");
							}
						}else {
							handleFail("Password was not locateable. Please check the error message",
									"password_locator_fail");
						}
					} else {
						handleFail("User Name was not locateable. Please check the error message",
								"userName_locator_fail");
					}
				} else {
					handleFail("Last Name was not locateable. Please check the error message", "lastName_locator_fail");
				}
			} else {
				handleFail("First Name was not locateable. Please check the error message.", "firstName_locator_fail");
			}
		} catch (Exception e) {
			handleFail("An error occurred during login. Please check the error message.", "login_error");
		}
	}
}
