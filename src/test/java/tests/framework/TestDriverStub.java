package tests.framework;

import org.openqa.selenium.WebDriver;

import java.lang.reflect.Proxy;

public final class TestDriverStub {

    private TestDriverStub() {
    }

    public static WebDriver newDriver() {
        return (WebDriver) Proxy.newProxyInstance(
                TestDriverStub.class.getClassLoader(),
                new Class[]{WebDriver.class},
                (proxy, method, args) -> {
                    Class<?> returnType = method.getReturnType();

                    if (returnType.equals(boolean.class)) {
                        return false;
                    }
                    if (returnType.equals(int.class)) {
                        return 0;
                    }
                    if (returnType.equals(long.class)) {
                        return 0L;
                    }
                    if (returnType.equals(double.class)) {
                        return 0D;
                    }
                    if (returnType.equals(float.class)) {
                        return 0F;
                    }
                    if (returnType.equals(short.class)) {
                        return (short) 0;
                    }
                    if (returnType.equals(byte.class)) {
                        return (byte) 0;
                    }
                    if (returnType.equals(char.class)) {
                        return '\0';
                    }
                    return null;
                }
        );
    }
}
