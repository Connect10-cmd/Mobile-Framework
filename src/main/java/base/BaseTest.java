package base;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import driver.DriverFactory;
import driver.DriverManager;
import utils.ReportManager;

import java.lang.reflect.Method;

public class BaseTest {

    protected WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method) {
        try {
            logger.info("Initializing driver for test {}", method.getName());
            driver = DriverFactory.getDriver();
            DriverManager.setDriver(driver);
        } catch (IllegalStateException e) {
            logger.warn("Skipping mobile test {} because driver setup failed: {}", method.getName(), e.getMessage());
            throw new SkipException("Skipping mobile test because Appium/device is unavailable: " + e.getMessage(), e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result != null) {
                if (result.getStatus() == ITestResult.FAILURE) {
                    logger.error("Test failed: {}", result.getName(), result.getThrowable());
                    ReportManager.attachScreenshot(driver, "failure-" + result.getName());
                    ReportManager.attachPageSource(driver, "page-source-" + result.getName());
                } else if (result.getStatus() == ITestResult.SUCCESS) {
                    logger.info("Test passed: {}", result.getName());
                } else if (result.getStatus() == ITestResult.SKIP) {
                    logger.warn("Test skipped: {}", result.getName());
                    ReportManager.attachText("skipped-" + result.getName(), "Test skipped. Reason: " + result.getThrowable());
                }
            }
        } finally {
            if (driver != null) {
                logger.info("Quitting driver");
                driver.quit();
            }
            DriverManager.unload();
        }
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
