package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================
    private final By appTitle = By.xpath("//*[contains(@content-desc,'Vevahik') or contains(@text,'Vevahik')]");
    private final By exploreProfilesBtn = By.xpath("//*[contains(@content-desc,'Explore') or contains(@text,'Explore')]");
    // Use contains to avoid trailing space issues
    private final By signUpBtn = By.xpath("//*[contains(@content-desc,'Sign Up') or contains(@text,'Sign Up')]");
    private final By loginBtn = By.xpath("//*[contains(@content-desc,'Login') or contains(@text,'Login')]");

    // ================= ACTIONS =================
    public boolean isAppLoaded() {
        return elementActions.isDisplayed(appTitle);
    }

    public void clickExploreProfiles() {
        elementActions.click(exploreProfilesBtn);
    }

    public void clickSignUp() {
        elementActions.clickFirst(
                signUpBtn,
                By.xpath("//*[contains(@content-desc,'Sign up') or contains(@text,'Sign up') or contains(@text,'Sign Up') or contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'sign up') ]"),
                By.xpath("//*[contains(@resource-id,'signup') or contains(@resource-id,'register')]")
        );
    }

    public void clickLogin() {
        elementActions.click(loginBtn);
    }
}