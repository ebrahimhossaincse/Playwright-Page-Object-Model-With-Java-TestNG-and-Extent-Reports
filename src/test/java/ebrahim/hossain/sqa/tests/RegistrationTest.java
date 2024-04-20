package ebrahim.hossain.sqa.tests;

import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import ebrahim.hossain.sqa.basedriver.BaseDriver;
import ebrahim.hossain.sqa.pages.RegisterPage;
import ebrahim.hossain.sqa.utilities.ExtentFactory;

public class RegistrationTest extends BaseDriver {

	ExtentReports report;
	ExtentTest parentTest;
	ExtentTest childTest;

	@BeforeClass
	@Parameters({ "url", "browserName", "headless" })
	public void openUrl(@Optional("https://www.tutorialspoint.com/selenium/practice/register.php") String url,
			@Optional("chrome") String browserName, @Optional("false") String headless) throws InterruptedException {
		report = ExtentFactory.getInstance();
		parentTest = report.createTest("<p style=\"color:#FF6000; font-size:20px\"><b>REGISTRATION MODULE</b></p>")
				.assignAuthor("QA TEAM").assignDevice("Windows");
		launchPlaywright(browserName, headless);
		launchApplication(url);
	}

	@Test(priority = 0)
	public void registrationTest() throws IOException {
		childTest = parentTest.createNode("<p style=\"color:#3E96E7; font-size:20px\"><b>REGISTRATION</b></p>");
		RegisterPage register = new RegisterPage(childTest, page);
		register.login();
	}

	@AfterClass
	public void afterClass() {
		closePlaywright();
		report.flush();
	}

}
