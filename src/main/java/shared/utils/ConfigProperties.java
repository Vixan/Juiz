package shared.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigProperties extends Properties {
    private static ConfigProperties instance = null;
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/config.properties";

    private ConfigProperties() {}

    public static ConfigProperties getInstance() {
        if (instance == null) {
            try {
                instance = new ConfigProperties();
                FileInputStream inputStream = new FileInputStream(PROPERTIES_FILE_PATH);
                instance.load(inputStream);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return instance;
    }

    public int getWindowWidth() {
        String width = instance.getProperty("WINDOW_WIDTH");

        return (width != null) ? Integer.parseInt(width) : 800;
    }

    public int getWindowHeight() {
        String height = instance.getProperty("WINDOW_HEIGHT");

        return (height != null) ? Integer.parseInt(height) : 800;
    }
}