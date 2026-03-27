package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public final class ReportManager {

    private ReportManager() {
    }

    public static void attachScreenshot(WebDriver driver, String attachmentName) {
        if (driver == null || !(driver instanceof TakesScreenshot)) {
            return;
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(attachmentName, new ByteArrayInputStream(screenshot));
        } catch (Exception ignored) {
        }
    }

    public static void attachPageSource(WebDriver driver, String attachmentName) {
        if (driver == null) {
            return;
        }

        try {
            String pageSource = driver.getPageSource();
            Allure.addAttachment(attachmentName, "text/xml", pageSource, ".xml");
        } catch (Exception ignored) {
        }
    }

    public static void attachText(String attachmentName, String content) {
        if (content == null) {
            return;
        }

        Allure.addAttachment(
                attachmentName,
                "text/plain",
                new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)),
                ".txt"
        );
    }
}
