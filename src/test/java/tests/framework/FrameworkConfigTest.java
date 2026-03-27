package tests.framework;

import config.FrameworkConfig;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class FrameworkConfigTest {

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        System.clearProperty("framework.test.key");
        System.clearProperty("framework.test.primary");
        System.clearProperty("framework.test.secondary");
    }

    @Test
    public void shouldPreferSystemPropertyValue() {
        System.setProperty("framework.test.key", "system-value");

        String value = FrameworkConfig.get("framework.test.key", "default-value");

        Assert.assertEquals(value, "system-value");
    }

    @Test
    public void shouldReturnFirstAvailableSystemProperty() {
        System.setProperty("framework.test.secondary", "secondary-value");

        String value = FrameworkConfig.getFirst("default-value", "framework.test.primary", "framework.test.secondary");

        Assert.assertEquals(value, "secondary-value");
    }
}
