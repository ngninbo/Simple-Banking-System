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
    private long balance;

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

    public CreditCardBuilder withBalance(long balance) {
        this.balance = balance;
        return this;
    }

    public Card build() {
        return new Card(creditCardNumber, pin, balance);
    }
}