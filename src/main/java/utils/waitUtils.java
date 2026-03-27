package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.android.AndroidDriver;

import java.time.Duration;

public class waitUtils {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public waitUtils(AndroidDriver driver, long timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public waitUtils(AndroidDriver driver) {
        this(driver, 10);
    }

    public void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForPresence(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean isElementPresent(By locator) {
        try {
            waitForPresence(locator);
            return true;
        } catch (Exception e) {
            System.out.println("WaitUtils.isElementPresent failed: " + e.getMessage());
            return false;
        }
    }
}
