package me.hossain.ebrahim.pages;

import java.nio.file.Paths;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import me.hossain.ebrahim.utilities.CommonMethods;

public class LoginPageNegativeTests extends CommonMethods{
	private Page page;
    private ExtentTest test;

    private ElementHandle username;
    private ElementHandle password;
    private ElementHandle login_button;

    public LoginPageNegativeTests(Page page, ExtentTest test) {
        this.page = page;
        this.test = test;

        this.username = page.querySelector("//input[@name='username']");
        this.password = page.querySelector("//input[@name='password']");
        this.login_button = page.querySelector("//button[@type='submit']");
    }

    // Helper methods for logging results
    public void handlePass(String message) {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
    }

    public void handleFail(String message, String screenshotName) {
        test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
        Throwable t = new InterruptedException("Exception");
        test.fail(t);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
                .setFullPage(true));
        String dest = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
        test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
    }

    // Negative Test Case: Invalid Username
    public void loginWithInvalidUsername() throws InterruptedException {
        test.info("Login Process with Invalid Username");
        try {
            if (username.isVisible()) {
                username.fill("invalidUser"); // Invalid username
                password.fill("admin123");    // Valid password
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with invalid username", "invalid_username");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with invalid username", "invalid_username_fail");
        }
    }

    // Negative Test Case: Invalid Password
    public void loginWithInvalidPassword() throws InterruptedException {
        test.info("Login Process with Invalid Password");
        try {
            if (username.isVisible()) {
                username.fill("Admin");       // Valid username
                password.fill("wrongPass123"); // Invalid password
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with invalid password", "invalid_password");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with invalid password", "invalid_password_fail");
        }
    }

    // Negative Test Case: Empty Username and Password
    public void loginWithEmptyUsernameAndPassword() throws InterruptedException {
        test.info("Login Process with Empty Username and Password");
        try {
            if (login_button.isVisible()) {
                login_button.click();         // Attempt login with empty fields
                Thread.sleep(5000);
                handleFail("Login failed as expected with empty username and password", "empty_username_password");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with empty fields", "empty_username_password_fail");
        }
    }

    // Negative Test Case: Empty Username
    public void loginWithEmptyUsername() throws InterruptedException {
        test.info("Login Process with Empty Username");
        try {
            if (password.isVisible()) {
                password.fill("admin123");    // Valid password
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with empty username", "empty_username");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with empty username", "empty_username_fail");
        }
    }

    // Negative Test Case: Empty Password
    public void loginWithEmptyPassword() throws InterruptedException {
        test.info("Login Process with Empty Password");
        try {
            if (username.isVisible()) {
                username.fill("Admin");       // Valid username
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with empty password", "empty_password");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with empty password", "empty_password_fail");
        }
    }

    // Negative Test Case: SQL Injection Attempt
    public void loginWithSQLInjection() throws InterruptedException {
        test.info("Login Process with SQL Injection Attempt");
        try {
            if (username.isVisible()) {
                username.fill("'; DROP TABLE users;--"); // Malicious input
                password.fill("admin123");    // Valid password
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with SQL injection attempt", "sql_injection");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with SQL injection", "sql_injection_fail");
        }
    }

    // Negative Test Case: Special Characters in Password
    public void loginWithSpecialCharactersInPassword() throws InterruptedException {
        test.info("Login Process with Special Characters in Password");
        try {
            if (username.isVisible()) {
                username.fill("Admin");       // Valid username
                password.fill("!@#$%^&*");    // Special characters in password
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with special characters in password", "special_chars_password");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with special characters", "special_chars_password_fail");
        }
    }

    // Negative Test Case: Invalid Username Format (Missing @)
    public void loginWithInvalidUsernameFormat() throws InterruptedException {
        test.info("Login Process with Invalid Username Format");
        try {
            if (username.isVisible()) {
                username.fill("invalidEmail"); // Missing @ symbol
                password.fill("admin123");     // Valid password
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with invalid email format", "invalid_email_format");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with invalid email format", "invalid_email_format_fail");
        }
    }

    // Negative Test Case: Username Exceeding Maximum Length
    public void loginWithLongUsername() throws InterruptedException {
        test.info("Login Process with Username Exceeding Maximum Length");
        try {
            if (username.isVisible()) {
                String longUsername = "a".repeat(256); // 256-character long username
                username.fill(longUsername);
                password.fill("admin123");     // Valid password
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with a long username", "long_username");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with long username", "long_username_fail");
        }
    }

    // Negative Test Case: Locked Account
    public void loginWithLockedAccount() throws InterruptedException {
        test.info("Login Process with Locked Account");
        try {
            if (username.isVisible()) {
                username.fill("lockedUser");   // Assume this is a locked account
                password.fill("admin123");     // Valid password
                login_button.click();
                Thread.sleep(5000);
                handleFail("Login failed as expected with locked account", "locked_account");
            }
        } catch (Exception e) {
            handleFail("An error occurred during login with locked account", "locked_account_fail");
        }
    }
}
