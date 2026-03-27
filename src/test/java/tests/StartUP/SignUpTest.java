package tests.StartUP;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.SignUpPage;

public class SignUpTest extends BaseTest {

    @Test(priority = 1)
    public void verifyValidSignUpFlow() {

        SignUpPage signUpPage = new SignUpPage(driver);

        Assert.assertTrue(signUpPage.isPageLoaded(), "Signup page not loaded");

        signUpPage.selectProfile("myself");
        signUpPage.selectGender("male");
        signUpPage.enterMobileNumber("9876543210");
        signUpPage.clickContinue();

        // TODO: Add next screen assertion when available
    }

    @Test(priority = 2)
    public void verifyInvalidMobileNumber() {

        SignUpPage signUpPage = new SignUpPage(driver);

        signUpPage.selectProfile("myself");
        signUpPage.selectGender("male");
        signUpPage.enterMobileNumber("123"); // invalid
        signUpPage.clickContinue();

        Assert.assertTrue(
                signUpPage.isMobileErrorDisplayed(),
                "Validation not shown for invalid mobile"
        );
    }
}