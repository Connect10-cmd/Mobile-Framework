package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // Locators
    private final By usernameField = AppiumBy.accessibilityId("username");
    private final By passwordField = AppiumBy.accessibilityId("password");
    private final By loginButton = AppiumBy.accessibilityId("login_button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return elementActions.isDisplayed(loginButton);
    }

    public void enterUsername(String username) {
        elementActions.sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        elementActions.sendKeys(passwordField, password);
    }

    public void tapLogin() {
        elementActions.click(loginButton);
    }
}
