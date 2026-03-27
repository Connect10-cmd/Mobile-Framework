package driver;

import config.FrameworkConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    private DriverFactory() {
    }

    public static UiAutomator2Options buildAndroidOptions() {
        String platformName = FrameworkConfig.getFirst("Android", "platform.name", "platformName");
        String automationName = FrameworkConfig.getFirst("UiAutomator2", "automation.name", "automationName");
        String deviceName = FrameworkConfig.getFirst("emulator-5554", "device.name", "deviceName");
        String udid = FrameworkConfig.getFirst("", "device.udid", "udid");
        String resolvedUdid = AndroidDeviceHelper.getConnectedDeviceOrFallback(udid);
        String appPackage = FrameworkConfig.getFirst("", "app.package", "appPackage");
        String appActivity = FrameworkConfig.getFirst("", "app.activity", "appActivity");
        String appPath = FrameworkConfig.getFirst("", "app.path", "appPath");
        boolean noReset = FrameworkConfig.getBoolean("noReset", true);
        long newCommandTimeout = FrameworkConfig.getLong("newCommandTimeout", 120L);

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(platformName)
                .setAutomationName(automationName)
                .setDeviceName(deviceName)
                .setNewCommandTimeout(Duration.ofSeconds(newCommandTimeout));

        if (resolvedUdid != null && !resolvedUdid.isBlank()) {
            options.setUdid(resolvedUdid);
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
        return options;
    }

    public static String getServerUrl() {
        return FrameworkConfig.get("appium.server", "http://127.0.0.1:4723");
    }

    public static boolean isServerReachable() {
        try {
            URL url = new URL(getServerUrl());
            int port = url.getPort() > 0 ? url.getPort() : url.getDefaultPort();
            if (port <= 0) {
                port = 4723;
            }

            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(url.getHost(), port), 1500);
                return true;
            }
        } catch (Exception e) {
            logger.warn("Appium server is not reachable at {}", getServerUrl());
            return false;
        }
    }

    public static WebDriver initializeDriver() {
        String server = getServerUrl();
        String deviceName = FrameworkConfig.getFirst("emulator-5554", "device.name", "deviceName");
        String udid = FrameworkConfig.getFirst("", "device.udid", "udid");
        String resolvedUdid = AndroidDeviceHelper.getConnectedDeviceOrFallback(udid);
        String appPackage = FrameworkConfig.getFirst("", "app.package", "appPackage");
        String appActivity = FrameworkConfig.getFirst("", "app.activity", "appActivity");

        if (!isServerReachable()) {
            throw new IllegalStateException("Appium server is not reachable at " + server);
        }

        try {
            AndroidDeviceHelper.wakeUpDevice(resolvedUdid);
            logger.info("Starting AndroidDriver session against {} (deviceName={}, udid={})", server, deviceName, resolvedUdid);
            return new AndroidDriver(new URL(server), buildAndroidOptions());
        } catch (Exception e) {
            String message = String.format(
                    "Failed to initialize Appium driver. server=%s, deviceName=%s, appPackage=%s, appActivity=%s",
                    server, deviceName, appPackage, appActivity
            );
            logger.error(message, e);
            throw new IllegalStateException(message, e);
        }
    }

    public static WebDriver getDriver() {
        return initializeDriver();
    }
}
