package banking.generator;

import banking.util.PropertiesLoader;

import java.io.IOException;

public class PinGenerator extends NumberGenerator {

    public static String generatePin(int min, int max) throws IOException {
        String pinFormat = PropertiesLoader.loadProperties("application.properties").getProperty("PIN_FORMATTER");
        return String.format(pinFormat, generate(min, max));
    }
}
