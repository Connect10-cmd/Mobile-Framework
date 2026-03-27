package tests.StartUP;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ExploreProfilesPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignUpPage;

public class VerifyLandingTest extends BaseTest {

    @Test(priority = 1, groups = "mobile")
    public void verifyAppLaunch() {
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(
                homePage.isAppLoaded(),
                "App did not load correctly - Title not visible"
        );
    }

    @Test(priority = 2, groups = "mobile")
    public void verifyNavigationOptions() {
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isAppLoaded(), "Home page did not load");

        ExploreProfilesPage exploreProfilesPage = homePage.goToExploreProfiles();
        Assert.assertTrue(exploreProfilesPage.isLoaded(), "Explore profiles page did not load");
        driver.navigate().back();

        SignUpPage signUpPage = homePage.goToSignUp();
        Assert.assertTrue(signUpPage.isLoaded(), "Sign up page did not load");
        driver.navigate().back();

        LoginPage loginPage = homePage.goToLogin();
        Assert.assertTrue(loginPage.isLoaded(), "Login page did not load");
    }
}
