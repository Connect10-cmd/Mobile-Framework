package tests.StartUP;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

class verifylanding extends BaseTest {

    @Test(priority = 1)
    public void verifyAppLaunch() {
        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(
                homePage.isAppLoaded(),
                "App did not load correctly - Title not visible"
        );
    }

    @Test(priority = 2)
    public void verifyNavigationOptions() {
        HomePage homePage = new HomePage(getDriver());

        homePage.clickExploreProfiles();
        // Could assert new page loaded here if page object exists
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        homePage.clickSignUp();
        driver.navigate().back();

        homePage.clickLogin();
        // Add assertion for login page if implemented
    }
}
