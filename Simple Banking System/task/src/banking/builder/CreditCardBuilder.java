package banking.builder;

import banking.generator.CreditCardNumberGenerator;
import banking.generator.PinGenerator;
import banking.models.Card;
import banking.util.PropertiesLoader;

import java.util.Properties;


/**
 * This class implements the card generation and validation logic.
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class CreditCardBuilder {

    private String creditCardNumber;
    private String pin;

    private final Properties properties;

    {
        properties = PropertiesLoader.getInstance().properties();
    }

    private CreditCardBuilder() {
    }

    public static CreditCardBuilder init() {
        return new CreditCardBuilder();
    }

    public CreditCardBuilder withCardNumber() {
        this.creditCardNumber = CreditCardNumberGenerator.generateCardNumber(
                Long.parseLong(properties.getProperty("BANK_IDENTIFICATION_NUMBER")),
                Long.parseLong(properties.getProperty("MIN_ACCOUNT_IDENTIFIER")),
                Long.parseLong(properties.getProperty("MAX_ACCOUNT_IDENTIFIER")));
        return this;
    }

    public CreditCardBuilder withPin() {
        this.pin = PinGenerator.generatePin(
                Integer.parseInt(properties.getProperty("MIN_PIN")),
                Integer.parseInt(properties.getProperty("MAX_PIN")));
        return this;
    }

    public Card build() {
        return Card.createCard(creditCardNumber, pin);
    }
}