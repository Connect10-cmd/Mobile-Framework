package tests.StartUP;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.HomePage;
import pages.SignUpPage;

import java.util.concurrent.ThreadLocalRandom;

public class SignUpTest extends BaseTest {

    private SignUpPage signUpPage;

    @BeforeMethod
    public void setupPage() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isAppLoaded(), "Home page not loaded");

        signUpPage = homePage.goToSignUp();
        Assert.assertTrue(signUpPage.isLoaded(), "Signup page not loaded");
    }

    @Test(priority = 1, groups = "mobile")
    public void verifyValidSignUpFlow() {

        signUpPage.selectProfile("myself");
        signUpPage.selectGender("male");

        signUpPage.enterMobileNumber(generateRandomMobile());
        signUpPage.clickContinue();

        // Assuming OTP screen appears
        signUpPage.enterOTP("1234"); // replace with real handling later
        signUpPage.clickVerifyAndContinue();

        Assert.assertTrue(
                signUpPage.isSuccessScreenDisplayed(),
                "Signup success screen not displayed"
        );
    }

    @Test(priority = 2, groups = "mobile")
    public void verifyInvalidMobileNumber() {

        signUpPage.selectProfile("myself");
        signUpPage.selectGender("male");

        signUpPage.enterMobileNumber("123");
        signUpPage.clickContinue();

        Assert.assertTrue(
                signUpPage.isMobileErrorDisplayed(),
                "Validation not shown for invalid mobile"
        );
    }

    // ================= UTIL =================

    private String generateRandomMobile() {
        return "9" + ThreadLocalRandom.current().nextInt(100_000_000, 1_000_000_000);
    }
}
