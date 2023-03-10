package banking.generator;

import banking.util.PropertiesLoader;

public class PinGenerator extends NumberGenerator {

    public static String generatePin(int min, int max) {
        String pinFormat = PropertiesLoader.getInstance().properties().getProperty("PIN_FORMATTER");
        return String.format(pinFormat, generate(min, max));
    }
}
