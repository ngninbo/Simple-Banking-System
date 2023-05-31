package banking.services;

import banking.models.Card;

import java.util.Optional;

public interface CardService {

    void saveCard(Card card);

    Optional<Card> findCardByNumber(String cardNumber);

    long findBalanceByCardNumber(String cardNumber);

    void addIncome(String cardNumber, long income);

    void deleteCardByNumber(String cardNumber);

    TransferResult transfer(long amount, String from, String to);
}
