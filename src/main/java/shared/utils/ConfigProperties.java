package shared.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigProperties extends Properties {
    private static ConfigProperties instance = null;
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/config.properties";

    private ConfigProperties() {
    }

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

    public String getWindowTitle() {
        String title = instance.getProperty("WINDOW_TITLE");

        return (title != null) ? title : "Juiz";
    }

    public int getWindowWidth() {
        String width = instance.getProperty("WINDOW_WIDTH");

        return (width != null) ? Integer.parseInt(width) : 800;
    }

    public int getWindowHeight() {
        String height = instance.getProperty("WINDOW_HEIGHT");

        return (height != null) ? Integer.parseInt(height) : 800;
    }

    public String getDbPath() {
        String dbPath = instance.getProperty("DB_PATH");

        return (dbPath != null) ? dbPath : "tmp/test.db";
    }

    public String getDbUsername() {
        String username = instance.getProperty("DB_USERNAME");

        return (username != null) ? username : "admin";
    }

    public String getDbPassword() {
        String password = instance.getProperty("DB_PASSWORD");

        return (password != null) ? password : "admin";
    }
}