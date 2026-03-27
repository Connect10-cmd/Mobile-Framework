package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExploreProfilesPage extends BasePage {

    private final By pageHeader = AppiumBy.accessibilityId("explore_profiles_header");
    private final By firstProfile = AppiumBy.accessibilityId("profile_0");

    public ExploreProfilesPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return elementActions.isDisplayed(pageHeader);
    }

    public void openFirstProfile() {
        elementActions.click(firstProfile);
    }
}
