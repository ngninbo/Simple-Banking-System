package banking.generator;

import static banking.util.CardGeneratorConstants.*;

public class PinGenerator extends NumberGenerator {

    public static String generatePin(int min, int max) {
        return String.format(PIN_FORMATTER, generate(min, max));
    }
}
