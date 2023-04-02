package banking.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final PropertiesLoader INSTANCE = new PropertiesLoader();

    private PropertiesLoader() {
    }

    public static PropertiesLoader getInstance() {
        return INSTANCE;
    }

    public Properties statements() {
        return getProperties("statements.properties");
    }

    public Properties messages() {
        return getProperties("messages.properties");
    }

    public Properties properties() {
        return getProperties("application.properties");
    }

    public Properties getProperties(String propertiesFilename) {
        return loadProperties(propertiesFilename);
    }

    private static Properties loadProperties(String resourceFileName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties configuration = new Properties();

        try (InputStream inputStream = loader.getResourceAsStream(resourceFileName)) {
            configuration.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configuration;
    }
}
