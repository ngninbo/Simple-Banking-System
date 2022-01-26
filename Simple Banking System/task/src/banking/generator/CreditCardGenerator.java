package banking.generator;

import banking.models.Card;
import banking.util.CreditCardNumberValidator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static banking.util.CardGeneratorConstants.*;


/**
 * This class implements the card generation and validation logic.
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class CreditCardGenerator {

    private String creditCardNumber;
    private String pin;
    private final Predicate<String> isValid = CreditCardNumberValidator::validate;

    private CreditCardGenerator() {
    }

    public static CreditCardGenerator init() {
        return new CreditCardGenerator();
    }

    /**
     * Generate a PIN number
     */
    public CreditCardGenerator createPin() {
        this.pin = generatePin();
        return this;
    }

    public CreditCardGenerator createCardNumber() {
        this.creditCardNumber = generateValidCreditCardNumber();
        return this;
    }

    public Card build() {
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
    public static int generateAccountIdentifier() {

        return (int) ThreadLocalRandom.current().nextLong(MIN_ACCOUNT_IDENTIFIER, MAX_ACCOUNT_IDENTIFIER);
    }

    /**
     * Generate card number and validate it
     */
    public String generateValidCreditCardNumber() {
        StringBuilder tmpCardNumber = new StringBuilder();
        int accountIdentifier = generateAccountIdentifier();
        tmpCardNumber.append(BANK_IDENTIFICATION_NUMBER).append(accountIdentifier);
        return validateCreditCardNumber(tmpCardNumber.toString());
    }

    /**
     * Validate generated card number while finding the check sum
     */
    public String validateCreditCardNumber(String number) {
        int checkSum = 0;
        while (isValid.negate().test(number + checkSum)) {
            checkSum += 1;
        }
        return number + checkSum;
    }
}