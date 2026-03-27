package driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Properties;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static WebDriver initializeDriver() {
        WebDriver driver = null;

        Properties props = new Properties();
        try {
            Path cfg = Path.of("config.properties");
            if (Files.exists(cfg)) {
                try (FileInputStream in = new FileInputStream(cfg.toFile())) {
                    props.load(in);
                }
            }
        } catch (Exception e) {
            logger.warn("Could not load config.properties, falling back to system properties", e);
        }

        String server = System.getProperty("appium.server", props.getProperty("appium.server", "http://127.0.0.1:4723"));
        String deviceName = System.getProperty("device.name", props.getProperty("device.name", "emulator-5554"));
        String udid = System.getProperty("device.udid", props.getProperty("device.udid", ""));
        String appPackage = System.getProperty("app.package", props.getProperty("app.package", ""));
        String appActivity = System.getProperty("app.activity", props.getProperty("app.activity", ""));
        String appPath = System.getProperty("app.path", props.getProperty("app.path", ""));
        boolean noReset = Boolean.parseBoolean(System.getProperty("noReset", props.getProperty("noReset", "true")));

        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setAutomationName("UiAutomator2")
                    .setDeviceName(deviceName)
                    .setNewCommandTimeout(Duration.ofSeconds(120));

            if (!udid.isBlank()) {
                options.setUdid(udid);
            }

            if (!appPath.isBlank()) {
                options.setApp(appPath);
            }

            if (!appPackage.isBlank()) {
                options.setCapability("appPackage", appPackage);
            }
            if (!appActivity.isBlank()) {
                options.setCapability("appActivity", appActivity);
            }

            options.setCapability("noReset", noReset);

            logger.info("Starting AndroidDriver session against {} (deviceName={})", server, deviceName);
            driver = new AndroidDriver(new URL(server), options);

        } catch (Exception e) {
            logger.error("Failed to initialize Appium driver", e);
        }

        return driver;
    }

    public static WebDriver getDriver() {
        return initializeDriver();
    }
}