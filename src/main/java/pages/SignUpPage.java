package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.appium.java_client.AppiumBy;


public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================

   private By mySelf = AppiumBy.accessibilityId("Myself");
    private By myDaughter = AppiumBy.accessibilityId("My Daughter");
    private By mySon = AppiumBy.accessibilityId("My Son");
    private By myBrother = AppiumBy.accessibilityId("My Brother");
    private By mySister = AppiumBy.accessibilityId("My Sister");
    private By myFriend = AppiumBy.accessibilityId("My Friend");
    private By myRelative = AppiumBy.accessibilityId("My Relative");

    private By male = AppiumBy.accessibilityId("Male");
    private By female = AppiumBy.accessibilityId("Female");

    private By continueBtn = AppiumBy.accessibilityId("Continue");
    private By tryAgainBtn = AppiumBy.accessibilityId("Try Again");

    private By mobileError = AppiumBy.accessibilityId("Mobile number must be at least 10 digits");

    // Fallback xpath
    private By mobileInput = By.xpath("//android.widget.EditText");

    // ================= ACTIONS =================

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
            case "brother":
                elementActions.click(myBrother);
                break;
            case "sister":
                elementActions.click(mySister);
                break;
            case "friend":
                elementActions.click(myFriend);
                break;
            case "relative":
                elementActions.click(myRelative);
                break;
            default:
                throw new IllegalArgumentException("Invalid profile type");
        }
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

    public void clickContinue() {
        elementActions.click(continueBtn);
    }

    public boolean isMobileErrorDisplayed() {
        return elementActions.isDisplayed(mobileError);
    }

    public void clickTryAgain() {
        elementActions.click(tryAgainBtn);
    }

    public boolean isPageLoaded() {
        return elementActions.isDisplayed(mySelf);
    }
}