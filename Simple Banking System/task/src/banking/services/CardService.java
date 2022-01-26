package banking.services;

import banking.models.Card;

import java.util.Optional;
import java.util.function.Predicate;

public interface CardService {

    void saveCard(Card card);

    Optional<Card> findCardByNumber(String cardNumber);

    long readBalanceByCardNumber(String cardNumber);

    void updateBalanceByCardNumber(String cardNumber, long income);

    void deleteCardByNumber(String cardNumber);

    void updateBalanceByCardNumber(String cardNumber, String targetCardNumber, long amount);

    boolean isCardAvailableByCardNumberAndPin(String cardNumber, String pin);

    boolean isCardByNumberPresent(String targetCardNumber);

    boolean isCardByNumberAndPinPresent(String cardNumber, String pin);

    Predicate<String> cardNumberPresentChecker();

    void createCard();
}
