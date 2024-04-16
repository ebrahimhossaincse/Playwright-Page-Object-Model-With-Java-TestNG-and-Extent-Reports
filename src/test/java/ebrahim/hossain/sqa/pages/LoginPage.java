package ebrahim.hossain.sqa.pages;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ebrahim.hossain.sqa.utilities.CommonMethods;

public class LoginPage extends CommonMethods {

	Page page;
	ExtentTest test;

	private Locator email;
	private Locator password;
	private Locator login_button;

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

	public LoginPage(ExtentTest test, Page page) {
		this.test = test;
		this.page = page;

		this.email = page.locator("//input[@name='username']");
		this.password = page.locator("//input[@name='password']");
		this.login_button = page.locator("//button[@type='submit']");
	}

}
