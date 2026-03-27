package driver;

import config.FrameworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class AndroidDeviceHelper {

    private static final Logger logger = LoggerFactory.getLogger(AndroidDeviceHelper.class);
    private static final String ADB_PATH = FrameworkConfig.get(
            "adb.path",
            "/Users/sachin/Library/Android/sdk/platform-tools/adb"
    );

    private AndroidDeviceHelper() {
    }

    public static void wakeUpDevice(String udid) {
        if (!FrameworkConfig.getBoolean("wake.device.before.session", true)) {
            logger.info("Device wake-up is disabled by configuration");
            return;
        }

        String resolvedUdid = getConnectedDeviceOrFallback(udid);
        if (resolvedUdid == null || resolvedUdid.isBlank()) {
            logger.warn("No connected Android device found for wake-up");
            return;
        }

        logger.info("Waking Android device before test session: {}", resolvedUdid);
        runAdbCommand(resolvedUdid, "shell", "input", "keyevent", "KEYCODE_WAKEUP");
        runAdbCommand(resolvedUdid, "shell", "wm", "dismiss-keyguard");
        runAdbCommand(resolvedUdid, "shell", "input", "keyevent", "82");
    }

    static String resolveTargetDevice(String preferredUdid) {
        if (preferredUdid != null && !preferredUdid.isBlank()) {
            return preferredUdid;
        }

        List<String> devices = listConnectedDevices();
        return devices.isEmpty() ? null : devices.get(0);
    }

    public static String getConnectedDeviceOrFallback(String preferredUdid) {
        List<String> devices = listConnectedDevices();
        if (devices.isEmpty()) {
            return null;
        }

        if (preferredUdid != null && !preferredUdid.isBlank() && devices.contains(preferredUdid)) {
            return preferredUdid;
        }

        if (preferredUdid != null && !preferredUdid.isBlank()) {
            logger.warn("Configured udid {} is not connected. Falling back to {}", preferredUdid, devices.get(0));
        }

        return devices.get(0);
    }

    static List<String> listConnectedDevices() {
        List<String> devices = new ArrayList<>();

        try {
            Process process = new ProcessBuilder(ADB_PATH, "devices").start();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.endsWith("\tdevice")) {
                        devices.add(line.split("\\s+")[0]);
                    }
                }
            }
            process.waitFor();
        } catch (Exception e) {
            logger.warn("Unable to list Android devices using adb", e);
        }

        return devices;
    }

    static void runAdbCommand(String udid, String... args) {
        List<String> command = new ArrayList<>();
        command.add(ADB_PATH);

        if (udid != null && !udid.isBlank()) {
            command.add("-s");
            command.add(udid);
        }

        for (String arg : args) {
            command.add(arg);
        }

        try {
            Process process = new ProcessBuilder(command).start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                logger.warn("adb command failed with exit code {}: {}", exitCode, String.join(" ", command));
            }
        } catch (Exception e) {
            logger.warn("Failed to execute adb command: {}", String.join(" ", command), e);
        }
    }
}
