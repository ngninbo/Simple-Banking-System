package banking.generator;

public class PinGenerator extends NumberGenerator {

    public static String generatePin(int min, int max) {
        String pinFormat = MESSAGE_FACTORY.from("PIN_FORMATTER");
        return String.format(pinFormat, generate(min, max));
    }

    public static String pin() {
        return generatePin(Integer.parseInt(MESSAGE_FACTORY.from("MIN_PIN")),
                Integer.parseInt(MESSAGE_FACTORY.from("MAX_PIN")));
    }
}
