package banking.generator;

import banking.util.CreditCardNumberValidator;

public class CreditCardNumberGenerator extends NumberGenerator {

    private static final CreditCardNumberGenerator INSTANCE = new CreditCardNumberGenerator();

    private CreditCardNumberGenerator() {
    }

    private final CreditCardNumberValidator validator = new CreditCardNumberValidator();

    private String generateCardNumber(long bankIdentificationNumber,
                                            long minAccountIdentifier,
                                            long maxAccountIdentifier) {
        long accountIdentifier = next(minAccountIdentifier, maxAccountIdentifier);
        String cardNumber = String.format("%s%s", bankIdentificationNumber, accountIdentifier);

        int checkSum = 0;

        while (!isValid(cardNumber + checkSum)) {
            checkSum += 1;
        }

        return cardNumber + checkSum;
    }

    private boolean isValid(String cardNumber) {
        return validator.setCreditCardNumber(cardNumber).validate();
    }

    private String generateCardNumber() {
        return generateCardNumber(Long.parseLong(PROPERTIES.getProperty("BANK_IDENTIFICATION_NUMBER")),
                Long.parseLong(PROPERTIES.getProperty("MIN_ACCOUNT_IDENTIFIER")),
                Long.parseLong(PROPERTIES.getProperty("MAX_ACCOUNT_IDENTIFIER")));
    }

    public static CreditCardNumberGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String next() {
        return generateCardNumber();
    }
}
