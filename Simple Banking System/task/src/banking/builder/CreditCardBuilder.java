package banking.builder;

import banking.models.Card;


/**
 * This class implements the card generation and validation logic.
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class CreditCardBuilder {

    private String creditCardNumber;
    private String pin;

    private CreditCardBuilder() {
    }

    public static CreditCardBuilder init() {
        return new CreditCardBuilder();
    }

    public CreditCardBuilder withCardNumber(String cardNumber) {
        this.creditCardNumber = cardNumber;
        return this;
    }

    public CreditCardBuilder withPin(String pin) {
        this.pin = pin;
        return this;
    }

    public Card build() {
        return CardFactory.createCard(creditCardNumber, pin);
    }
}