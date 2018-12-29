package shared.utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * The singleton in charge of project configuration using Java {@link Properties}.
 * Configuration properties are read from a <i>.properties</i> file and exposed through
 * public methods to the whole project.
 * <br/><br/>
 * <i>Example:</i> window sizes and database configuration can be retrieved using
 * this class's instance.
 */
public class ConfigProperties extends Properties {
    private static ConfigProperties instance = null;
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/config/config.properties";

    private ConfigProperties() {
    }

    /**
     * Create or retrieve the instance of the {@link ConfigProperties} singleton.
     * The instance is loaded with the <i>.properties</i> file data.
     *
     * @see Properties
     * @return instance of the {@link ConfigProperties} singleton.
     */
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

    /**
     * Retrieve the default window title, specified in the configuration file.
     *
     * @see Properties
     * @return the default window title.
     */
    public String getWindowTitle() {
        String title = instance.getProperty("WINDOW_TITLE");

        return (title != null) ? title : "Juiz";
    }

    /**
     * Retrieve the default window width, specified in the configuration file.
     *
     * @see Properties
     * @return the default window width.
     */
    public int getWindowWidth() {
        String width = instance.getProperty("WINDOW_WIDTH");

        return (width != null) ? Integer.parseInt(width) : 800;
    }

    /**
     * Retrieve the default window height, specified in the configuration file.
     *
     * @see Properties
     * @return the default window height.
     */
    public int getWindowHeight() {
        String height = instance.getProperty("WINDOW_HEIGHT");

        return (height != null) ? Integer.parseInt(height) : 800;
    }

    /**
     * Retrieve the default database file path, specified in the configuration file.
     *
     * @see Properties
     * @return the default database file path.
     */
    public String getDbPath() {
        String dbPath = instance.getProperty("DB_PATH");

        return (dbPath != null) ? dbPath : "tmp/test.db";
    }

    /**
     * Retrieve the default database user name, specified in the configuration file.
     *
     * @see Properties
     * @return the default database user name.
     */
    public String getDbUsername() {
        String username = instance.getProperty("DB_USERNAME");

        return (username != null) ? username : "admin";
    }

    /**
     * Retrieve the default database user password, specified in the configuration file.
     *
     * @see Properties
     * @return the default database user password.
     */
    public String getDbPassword() {
        String password = instance.getProperty("DB_PASSWORD");

        return (password != null) ? password : "admin";
    }
}