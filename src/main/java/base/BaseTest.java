package base;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import driver.DriverFactory;

import java.io.ByteArrayInputStream;

public class BaseTest {

    protected WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeMethod
    public void setup() {
        logger.info("Initializing driver for test");
        driver = DriverFactory.getDriver();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result != null && result.getStatus() == ITestResult.FAILURE && driver != null) {
                logger.error("Test failed: {}. Capturing screenshot.", result.getName());
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("screenshot-" + result.getName(), new ByteArrayInputStream(screenshot));
                } catch (Exception ex) {
                    logger.error("Failed to capture screenshot for Allure", ex);
                }
            }
        } finally {
            if (driver != null) {
                logger.info("Quitting driver");
                driver.quit();
            }
        }
    }

    protected WebDriver getDriver() {
        return this.driver;
    }
}