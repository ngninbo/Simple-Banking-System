package banking.generator;

public class PinGenerator extends NumberGenerator {

    public static String generatePin(int min, int max) {
        String pinFormat = PROPERTIES.getProperty("PIN_FORMATTER");
        return String.format(pinFormat, generate(min, max));
    }

    public static String pin() {
        return generatePin(Integer.parseInt(PROPERTIES.getProperty("MIN_PIN")),
                Integer.parseInt(PROPERTIES.getProperty("MAX_PIN")));
    }
}
