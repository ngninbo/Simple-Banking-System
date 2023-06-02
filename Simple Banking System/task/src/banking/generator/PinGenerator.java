package banking.generator;

public class PinGenerator extends NumberGenerator {

    private static final PinGenerator INSTANCE = new PinGenerator();

    private PinGenerator() {
    }

    private String generatePin(int min, int max) {
        String pinFormat = PROPERTIES.getProperty("PIN_FORMATTER");
        return String.format(pinFormat, next(min, max));
    }

    private String pin() {
        return generatePin(Integer.parseInt(PROPERTIES.getProperty("MIN_PIN")),
                Integer.parseInt(PROPERTIES.getProperty("MAX_PIN")));
    }

    public static PinGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String next() {
        return pin();
    }
}
