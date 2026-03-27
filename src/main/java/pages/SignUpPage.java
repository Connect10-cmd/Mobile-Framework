package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends BasePage {

    private final By signUpForm = AppiumBy.accessibilityId("signup_form");
    private final By nameField = AppiumBy.accessibilityId("name");
    private final By emailField = AppiumBy.accessibilityId("email");
    private final By signUpButton = AppiumBy.accessibilityId("signup_button");

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return elementActions.isDisplayed(signUpForm);
    }

    public void enterName(String name) {
        elementActions.sendKeys(nameField, name);
    }

    public void enterEmail(String email) {
        elementActions.sendKeys(emailField, email);
    }

    public void tapSignUp() {
        elementActions.click(signUpButton);
    }
}
