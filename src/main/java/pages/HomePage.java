package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String LOWERCASE_XPATH =
            "translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')";
    private static final String LOWERCASE_DESC_XPATH =
            "translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================
    private final By appTitle = By.xpath(
            "//*[contains(@content-desc,'Vevahik') or contains(@text,'Vevahik') or contains(" + LOWERCASE_XPATH + ",'vevahik')]"
    );
    private final By exploreProfilesBtn = By.xpath(
            "//*[contains(@content-desc,'Explore') or contains(@text,'Explore') or contains(" + LOWERCASE_XPATH + ",'explore') " +
                    "or contains(" + LOWERCASE_XPATH + ",'profiles') or contains(" + LOWERCASE_XPATH + ",'browse') " +
                    "or contains(" + LOWERCASE_DESC_XPATH + ",'explore') or contains(" + LOWERCASE_DESC_XPATH + ",'profiles') " +
                    "or contains(" + LOWERCASE_DESC_XPATH + ",'browse')]"
    );
    private final By signUpBtn = By.xpath(
            "//*[contains(@content-desc,'Sign Up') or contains(@text,'Sign Up') or contains(" + LOWERCASE_XPATH + ",'sign up') " +
                    "or contains(" + LOWERCASE_XPATH + ",'signup') or contains(" + LOWERCASE_XPATH + ",'register') " +
                    "or contains(" + LOWERCASE_DESC_XPATH + ",'sign up') or contains(" + LOWERCASE_DESC_XPATH + ",'signup') " +
                    "or contains(" + LOWERCASE_DESC_XPATH + ",'register')]"
    );
    private final By loginBtn = By.xpath(
            "//*[contains(@content-desc,'Login') or contains(@text,'Login') or contains(" + LOWERCASE_XPATH + ",'log in') " +
                    "or contains(" + LOWERCASE_XPATH + ",'login') or contains(" + LOWERCASE_DESC_XPATH + ",'log in') " +
                    "or contains(" + LOWERCASE_DESC_XPATH + ",'login')]"
    );

    // ================= ACTIONS =================
    public boolean isAppLoaded() {
        return elementActions.isDisplayed(appTitle);
    }

    public void clickExploreProfiles() {
        elementActions.clickFirst(
                exploreProfilesBtn,
                By.xpath("//*[contains(@resource-id,'explore') or contains(@resource-id,'profile') or contains(@resource-id,'browse')]"),
                By.xpath("//*[contains(@hint,'Explore') or contains(@hint,'Profile') or contains(@hint,'Browse')]")
        );
    }

    public ExploreProfilesPage goToExploreProfiles() {
        clickExploreProfiles();
        return new ExploreProfilesPage(driver);
    }

    public void clickSignUp() {
        elementActions.clickFirst(
                signUpBtn,
                By.xpath("//*[contains(@resource-id,'signup') or contains(@resource-id,'sign_up') or contains(@resource-id,'register')]"),
                By.xpath("//*[contains(@hint,'Sign') or contains(@hint,'Register')]")
        );
    }

    public SignUpPage goToSignUp() {
        clickSignUp();
        return new SignUpPage(driver);
    }

    public void clickLogin() {
        elementActions.clickFirst(
                loginBtn,
                By.xpath("//*[contains(@resource-id,'login') or contains(@resource-id,'signin') or contains(@resource-id,'sign_in')]")
        );
    }

    public LoginPage goToLogin() {
        clickLogin();
        return new LoginPage(driver);
    }
}
