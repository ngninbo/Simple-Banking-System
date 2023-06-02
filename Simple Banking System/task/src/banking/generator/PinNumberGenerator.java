package banking.generator;

public class PinNumberGenerator extends NumberGenerator {

    public PinNumberGenerator() {
    }

    private String generatePin(int min, int max) {
        String pinFormat = PROPERTIES.getProperty("PIN_FORMATTER");
        return String.format(pinFormat, next(min, max));
    }

    private String pin() {
        return generatePin(Integer.parseInt(PROPERTIES.getProperty("MIN_PIN")),
                Integer.parseInt(PROPERTIES.getProperty("MAX_PIN")));
    }

    @Override
    public String next() {
        return pin();
    }
}
