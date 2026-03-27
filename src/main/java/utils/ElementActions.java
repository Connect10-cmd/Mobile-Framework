package utils;

import config.FrameworkConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class ElementActions {

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    public ElementActions(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null while creating ElementActions");
        }
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver, FrameworkConfig.getLong("element.timeout.seconds", 15L));
    }

    // ================= CLICK =================

    public void click(By locator) {
        WebElement el = waitUtils.waitForClickable(locator);
        el.click();
    }

    public void safeClick(By locator) {
        try {
            click(locator);
        } catch (Exception e) {
            WebElement el = waitUtils.waitForVisibility(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    public void clickFirst(By... locators) {
        for (By locator : locators) {
            try {
                click(locator);
                return;
            } catch (Exception ignored) {}
        }
        throw new TimeoutException("None of the provided locators were clickable");
    }

    // ================= INPUT =================

    public void type(By locator, String text) {
        WebElement el = waitUtils.waitForVisibility(locator);
        el.clear();
        el.sendKeys(text);
    }

    public void sendKeys(By locator, String text) {
        type(locator, text);
    }

    // ================= VALIDATION =================

    public boolean isDisplayed(By locator) {
        try {
            waitUtils.waitForVisibility(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getText(By locator) {
        return waitUtils.waitForVisibility(locator).getText();
    }

    // ================= ADVANCED =================

    public WebElement findFirst(By... locators) {
        for (By locator : locators) {
            try {
                return waitUtils.waitForVisibility(locator);
            } catch (Exception ignored) {}
        }
        throw new NoSuchElementException("None of the locators found");
    }

    public void waitForInvisibility(By locator) {
        waitUtils.waitForInvisibility(locator);
    }
}
