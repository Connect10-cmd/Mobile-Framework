package tests.framework;

import driver.DriverFactory;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class DriverFactoryTest {

    @AfterMethod(alwaysRun = true)
    public void clearProperties() {
        System.clearProperty("appium.server");
        System.clearProperty("platformName");
        System.clearProperty("automationName");
        System.clearProperty("device.name");
        System.clearProperty("device.udid");
        System.clearProperty("app.package");
        System.clearProperty("app.activity");
        System.clearProperty("app.path");
        System.clearProperty("noReset");
        System.clearProperty("newCommandTimeout");
    }

    @Test
    public void shouldBuildAndroidOptionsFromSystemProperties() {
        System.setProperty("platformName", "Android");
        System.setProperty("automationName", "UiAutomator2");
        System.setProperty("device.name", "Pixel-API");
        System.setProperty("device.udid", "emulator-5554");
        System.setProperty("app.package", "com.example.app");
        System.setProperty("app.activity", ".SplashActivity");
        System.setProperty("noReset", "false");
        System.setProperty("newCommandTimeout", "45");

        UiAutomator2Options options = DriverFactory.buildAndroidOptions();

        Assert.assertEquals(String.valueOf(options.getCapability("platformName")).toUpperCase(), "ANDROID");
        Assert.assertEquals(String.valueOf(options.getCapability("appium:automationName")), "UiAutomator2");
        Assert.assertEquals(String.valueOf(options.getCapability("appium:deviceName")), "Pixel-API");
        Assert.assertEquals(String.valueOf(options.getCapability("appium:udid")), "emulator-5554");
        Assert.assertEquals(String.valueOf(options.getCapability("appium:appPackage")), "com.example.app");
        Assert.assertEquals(String.valueOf(options.getCapability("appium:appActivity")), ".SplashActivity");
        Assert.assertEquals(String.valueOf(options.getCapability("appium:noReset")), "false");
    }

    @Test
    public void shouldUseServerUrlFromSystemProperties() {
        System.setProperty("appium.server", "http://127.0.0.1:4999");

        Assert.assertEquals(DriverFactory.getServerUrl(), "http://127.0.0.1:4999");
    }
}
