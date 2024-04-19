package ebrahim.hossain.sqa.pages;

import java.io.IOException;
import java.nio.file.Paths;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ebrahim.hossain.sqa.utilities.CommonMethods;

public class LoginPage extends CommonMethods {

	Page page;
	ExtentTest test;

	private Locator email;
	private Locator password;
	private Locator login_button;

	public LoginPage(ExtentTest test, Page page) {
		this.page = page;
		this.test = test;

		this.email = page.locator("//input[@id='email']");
		this.password = page.locator("//input[@id='password']");
		this.login_button = page.locator("//input[@value='Login']");
	}

	public Locator getEmail() {
		return email;
	}

	public void setEmail(Locator email) {
		this.email = email;
	}

	public Locator getPassword() {
		return password;
	}

	public void setPassword(Locator password) {
		this.password = password;
	}

	public Locator getLogin_button() {
		return login_button;
	}

	public void setLogin_button(Locator login_button) {
		this.login_button = login_button;
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
			test.info("Login Page");
			if (email.isVisible()) {
				test.info("Please Enter your email address");
				email.fill("ebrahim@noemail.com");
				page.waitForTimeout(1000);
				handlePass("You have successfully entered your Email");
				if (password.isVisible()) {
					test.info("Please Enter your password");
					password.fill("test@123");
					page.waitForTimeout(1000);
					handlePass("You have successfully entered your Password");
					if (login_button.isVisible()) {
						login_button.click();
						page.waitForTimeout(1000);
						handlePass("You have successfully clicked on the Login");
					} else {
						handleFail("Login Button was not locateable. Please check the error message",
								"password_locator_fail");
					}
				} else {
					handleFail("Password was not locateable. Please check the error message", "password_locator_fail");
				}
			} else {
				handleFail("Email Address was not locateable. Please check the error message.", "email_locator_fail");
			}
		} catch (Exception e) {
			handleFail("An error occurred during login. Please check the error message.", "login_error");
		}
	}
}
