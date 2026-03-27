package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(groups = "mobile")
    public void launchTest() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isAppLoaded(), "Home page did not load");

        LoginPage loginPage = homePage.goToLogin();
        Assert.assertTrue(loginPage.isLoaded(), "Login page did not load");
    }
}
