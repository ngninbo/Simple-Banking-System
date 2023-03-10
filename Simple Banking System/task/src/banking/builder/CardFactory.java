package banking.builder;

import banking.models.Card;

public class CardFactory {

    public static Card createCard(String creditCardNumber, String pin) {
        return new Card(creditCardNumber, pin);
    }

    public static Card createCard(String creditCardNumber, String pin, long balance) {
        return new Card(creditCardNumber, pin, balance);
    }
}
