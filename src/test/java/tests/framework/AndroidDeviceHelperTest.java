package tests.framework;

import driver.AndroidDeviceHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class AndroidDeviceHelperTest {

    @Test
    public void shouldPreferProvidedUdidWhenResolvingTargetDevice() throws Exception {
        Method resolveMethod = AndroidDeviceHelper.class.getDeclaredMethod("resolveTargetDevice", String.class);
        resolveMethod.setAccessible(true);

        String udid = (String) resolveMethod.invoke(null, "physical-device-01");

        Assert.assertEquals(udid, "physical-device-01");
    }

    @Test
    public void shouldFallbackToConnectedDeviceWhenConfiguredUdidIsUnavailable() {
        String resolvedUdid = AndroidDeviceHelper.getConnectedDeviceOrFallback("missing-device");

        if (resolvedUdid != null) {
            Assert.assertNotEquals(resolvedUdid, "missing-device");
        }
    }
}
