package banking.generator;

import banking.util.PropertiesLoader;

public class PinGenerator extends NumberGenerator {

    public static String generatePin(int min, int max) {
        String pinFormat = PropertiesLoader.getInstance().properties().getProperty("PIN_FORMATTER");
        return String.format(pinFormat, generate(min, max));
    }

    public static String pin() {
        return PinGenerator.generatePin(
                Integer.parseInt(PROPERTIES.getProperty("MIN_PIN")),
                Integer.parseInt(PROPERTIES.getProperty("MAX_PIN")));
    }
}
