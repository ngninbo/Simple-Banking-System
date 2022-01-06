package banking.models;

/**
 * This is the base class of the simple banking system
 * @author Beauclair Dongmo Ngnintedem
 */
public class Card {

    private final String creditCardNumber;
    private final String pin;
    private final long balance;


    public Card(String creditCardNumber, String pin, long balance) {
        this.creditCardNumber = creditCardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    private Card(String creditCardNumber, String pin) {
        this (creditCardNumber, pin, 0L);
    }

    public static Card createCard(String creditCardNumber, String pin) {
        return new Card(creditCardNumber, pin);
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getPin() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }
}

