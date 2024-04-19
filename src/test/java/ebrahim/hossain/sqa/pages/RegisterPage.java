package ebrahim.hossain.sqa.pages;

import java.io.IOException;
import java.nio.file.Paths;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ebrahim.hossain.sqa.utilities.CommonMethods;

public class RegisterPage extends CommonMethods {

	Page page;
	ExtentTest test;

	private ElementHandle firstName;
	private ElementHandle lastName;
	private ElementHandle userName;
	private ElementHandle password;
	private ElementHandle register_button;
	private ElementHandle backToLogin_button;
	
	public RegisterPage(ExtentTest test, Page page) {
		this.page = page;
		this.test = test;
		
		this.firstName = page.querySelector(".firstname");
		this.lastName = page.querySelector(".lastname");
		this.userName = page.querySelector(".username");
		this.password = page.querySelector(".password");
		this.register_button = page.querySelector("//input[@type='submit']");
		this.backToLogin_button = page.querySelector("//a[@type='submit']");
	}

	public ElementHandle getFirstName() {
		return firstName;
	}

	public void setFirstName(ElementHandle firstName) {
		this.firstName = firstName;
	}

	public ElementHandle getLastName() {
		return lastName;
	}

	public void setLastName(ElementHandle lastName) {
		this.lastName = lastName;
	}

	public ElementHandle getUserName() {
		return userName;
	}

	public void setUserName(ElementHandle userName) {
		this.userName = userName;
	}

	public ElementHandle getPassword() {
		return password;
	}

	public void setPassword(ElementHandle password) {
		this.password = password;
	}

	public ElementHandle getRegister_button() {
		return register_button;
	}

	public void setRegister_button(ElementHandle register_button) {
		this.register_button = register_button;
	}

	public ElementHandle getBackToLogin_button() {
		return backToLogin_button;
	}

	public void setBackToLogin_button(ElementHandle backToLogin_button) {
		this.backToLogin_button = backToLogin_button;
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
		boolean flag= false;
		try {
			test.info("Registration Page");
			if (email.isVisible() && flag == false) {
				test.info("Please Enter your email address");
				email.fill("atp.dev.adm1n02@gmail.com");
				page.waitForTimeout(1000);
				handlePass("You have successfully entered your Email");
				flag = true;
				if (password.isVisible() && flag == true) {
					test.info("Please Enter your password");
					password.fill("@ATP!tester$001");
					page.waitForTimeout(1000);
					handlePass("You have successfully entered your Password");
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
