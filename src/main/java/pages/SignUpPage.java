package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends BasePage {

    private final By pageHeader = AppiumBy.accessibilityId("Continue");
    private final By fallbackHeader = By.xpath(
            "//*[contains(@content-desc,'Continue') or contains(@text,'Continue') or contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'mobile')]"
    );

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================

    // Profile चयन
    private By mySelf = AppiumBy.accessibilityId("Myself");
    private By myDaughter = AppiumBy.accessibilityId("My Daughter");
    private By mySon = AppiumBy.accessibilityId("My Son");
    private By myFriend = AppiumBy.accessibilityId("My Friend");

    // Gender
    private By male = AppiumBy.accessibilityId("Male");
    private By female = AppiumBy.accessibilityId("Female");

    // Buttons
    private By continueBtn = AppiumBy.accessibilityId("Continue");
    private By tryAgainBtn = AppiumBy.accessibilityId("Try Again");
    private By verifyContinueBtn = AppiumBy.accessibilityId("Verify & Continue");

    // Inputs (IMPORTANT: differentiate)
    private By mobileInput = By.xpath("(//android.widget.EditText)[1]");
    private By otpInput = By.xpath("(//android.widget.EditText)[2]");

    // Errors
    private By mobileError = AppiumBy.accessibilityId("Mobile number must be at least 10 digits");

    // Success Screen
    private By successScreen = AppiumBy.accessibilityId("Success");

    // ================= ACTIONS =================

    public boolean isLoaded() {
        return elementActions.isDisplayed(pageHeader)
                || elementActions.isDisplayed(fallbackHeader)
                || isProfileOptionsVisible();
    }

    public boolean isPageLoaded() {
        return isLoaded();
    }

    public void selectProfile(String profileType) {
        switch (profileType.toLowerCase()) {
            case "myself":
                elementActions.click(mySelf);
                break;
            case "daughter":
                elementActions.click(myDaughter);
                break;
            case "son":
                elementActions.click(mySon);
                break;
            case "friend":
                elementActions.click(myFriend);
                break;
            default:
                throw new IllegalArgumentException("Invalid profile type");
        }
    }

    public boolean isProfileOptionsVisible() {
        return elementActions.isDisplayed(mySelf);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            elementActions.click(male);
        } else if (gender.equalsIgnoreCase("female")) {
            elementActions.click(female);
        } else {
            throw new IllegalArgumentException("Invalid gender");
        }
    }

    public void enterMobileNumber(String number) {
        elementActions.type(mobileInput, number);
    }

    public boolean isMobileErrorDisplayed() {
        return elementActions.isDisplayed(mobileError);
    }

    public void clickContinue() {
        elementActions.click(continueBtn);
    }

    public void clickTryAgain() {
        elementActions.click(tryAgainBtn);
    }

    public void enterOTP(String otp) {
        elementActions.type(otpInput, otp);
    }

    public void clickVerifyAndContinue() {
        elementActions.click(verifyContinueBtn);
    }

    public boolean isSuccessScreenDisplayed() {
        return elementActions.isDisplayed(successScreen);
    }
}
