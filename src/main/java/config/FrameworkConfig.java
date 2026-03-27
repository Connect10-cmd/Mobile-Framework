package config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class FrameworkConfig {

    private static final Properties PROPERTIES = loadProperties();

    private FrameworkConfig() {
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }

    public static String getFirst(String defaultValue, String... keys) {
        for (String key : keys) {
            String value = System.getProperty(key);
            if (hasValue(value)) {
                return value;
            }

            value = PROPERTIES.getProperty(key);
            if (hasValue(value)) {
                return value;
            }
        }
        return defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
    }

    public static long getLong(String key, long defaultValue) {
        return Long.parseLong(get(key, String.valueOf(defaultValue)));
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        String externalConfigPath = System.getProperty("config.file");
        if (hasValue(externalConfigPath)) {
            loadFromPath(properties, Path.of(externalConfigPath));
        }

        if (properties.isEmpty()) {
            try (InputStream inputStream = FrameworkConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (inputStream != null) {
                    properties.load(inputStream);
                }
            } catch (IOException ignored) {
            }
        }

        if (properties.isEmpty()) {
            loadFromPath(properties, Path.of("config.properties"));
        }

        return properties;
    }

    private static void loadFromPath(Properties properties, Path path) {
        if (!Files.exists(path)) {
            return;
        }

        try (InputStream inputStream = Files.newInputStream(path)) {
            properties.load(inputStream);
        } catch (IOException ignored) {
        }
    }

    private static boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }
}
