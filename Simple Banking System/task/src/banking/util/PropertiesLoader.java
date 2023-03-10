package banking.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesLoader {

    private static final PropertiesLoader INSTANCE = new PropertiesLoader();

    private PropertiesLoader() {
    }

    public static PropertiesLoader getInstance() {
        return INSTANCE;
    }

    public Properties statements() {
        try {
            return loadProperties("statements.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Properties();
    }

    public Properties messages() {
        try {
            return loadProperties("logs.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Properties();
    }

    public Properties properties() {
        try {
            return loadProperties("application.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Properties();
    }

    private static Properties loadProperties(String resourceFileName) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties configuration = new Properties();
        InputStream inputStream = loader
                .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        Objects.requireNonNull(inputStream).close();
        return configuration;
    }
}
