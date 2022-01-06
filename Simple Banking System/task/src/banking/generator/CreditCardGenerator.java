package banking.generator;

import banking.models.Card;
import banking.services.CardService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements the card generation and validation logic.
 * @author Beauclair Dongmo Ngnintedem
 */
public class CreditCardGenerator {

    private int accountIdentifier;
    private final String pin;
    private String creditCardNumber;
    private static final int BANK_IDENTIFICATION_NUMBER = 400000;
    private List<Integer> usedAccountIdentifiers;


    private CreditCardGenerator() {
        generateCreditCardNumber();
        this.pin = generatePin();
    }

    public static CreditCardGenerator createCreditCardGenerator() {
        return new CreditCardGenerator();
    }

    public Card getCard() {
        return Card.createCard(creditCardNumber, pin);
    }

    /**
     * Generate a PIN number
     */
    public static String generatePin() {
        return String.format("%04d" , (int) ThreadLocalRandom.current().nextLong(0, 9999));
    }

    /**
     * Generate account identifier
     */
    private void generateAccountIdentifier() {

        accountIdentifier = (int) ThreadLocalRandom.current().nextLong(100000000, 999999999);

        if (!usedAccountIdentifiers.contains(accountIdentifier)) {
            usedAccountIdentifiers.add(accountIdentifier);
        } else {
            generateAccountIdentifier();
        }
    }

    /**
     * Generate card number and validate it
     */
    private void generateCreditCardNumber() {
        this.usedAccountIdentifiers = new ArrayList<>();
        StringBuilder tmpCardNumber = new StringBuilder();
        generateAccountIdentifier();
        tmpCardNumber.append(BANK_IDENTIFICATION_NUMBER).append(accountIdentifier);
        this.creditCardNumber = tmpCardNumber.toString();
        validateCreditCardNumber();
    }

    /**
     * Validate generated card number while finding the check sum
     */
    private void validateCreditCardNumber() {
        int checkSum = 0;
        while (!CardService.checkCreditCardNumber(this.creditCardNumber + checkSum)) {
            checkSum += 1;
        }
        this.creditCardNumber = this.creditCardNumber + checkSum;
    }
}