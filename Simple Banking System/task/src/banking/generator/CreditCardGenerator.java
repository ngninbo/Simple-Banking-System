package banking.generator;

import banking.models.Card;
import banking.util.CreditCardNumberValidator;

import static banking.util.CardGeneratorConstants.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;


/**
 * This class implements the card generation and validation logic.
 * @author Beauclair Dongmo Ngnintedem
 */
public class CreditCardGenerator {

    private final String creditCardNumber;
    private final String pin;
    private final Predicate<String> isValid = CreditCardNumberValidator::validate;

    private CreditCardGenerator() {
        this.creditCardNumber = generateValidCreditCardNumber();
        this.pin = generatePin();
    }

    public static CreditCardGenerator init() {
        return new CreditCardGenerator();
    }

    public Card getCard() {
        return Card.createCard(creditCardNumber, pin);
    }

    /**
     * Generate a PIN number
     */
    public static String generatePin() {
        return String.format(PIN_FORMATTER, (int) ThreadLocalRandom.current().nextLong(MIN_PIN, MAX_PIN));
    }

    /**
     * Generate account identifier
     */
    private static int generateAccountIdentifier() {

        return (int) ThreadLocalRandom.current().nextLong(MIN_ACCOUNT_IDENTIFIER, MAX_ACCOUNT_IDENTIFIER);
    }

    /**
     * Generate card number and validate it
     */
    private String generateValidCreditCardNumber() {
        StringBuilder tmpCardNumber = new StringBuilder();
        int accountIdentifier = generateAccountIdentifier();
        tmpCardNumber.append(BANK_IDENTIFICATION_NUMBER).append(accountIdentifier);
        return validateCreditCardNumber(tmpCardNumber.toString());
    }

    /**
     * Validate generated card number while finding the check sum
     */
    private String validateCreditCardNumber(String number) {
        int checkSum = 0;
        while (isValid.negate().test(number + checkSum)) {
            checkSum += 1;
        }
        return number + checkSum;
    }
}