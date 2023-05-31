package banking.services;

import banking.builder.CreditCardBuilder;
import banking.domain.MessageFactory;
import banking.generator.CreditCardNumberGenerator;
import banking.generator.PinGenerator;
import banking.models.Card;
import banking.util.CreditCardNumberValidator;

import java.util.Optional;
import java.util.function.BiPredicate;

public interface CardService {

    void saveCard(Card card);

    Optional<Card> findCardByNumber(String cardNumber);

    long findBalanceByCardNumber(String cardNumber);

    void addIncome(String cardNumber, long income);

    void deleteCardByNumber(String cardNumber);

    TransferResult transfer(long amount, String from, String to);

    boolean isCardWithNumberAndPinPresent(String cardNumber, String pin);

    default boolean isCardWithNumberPresent(String targetCardNumber) {
        return findCardByNumber(targetCardNumber).isPresent();
    }

    default BiPredicate<String, String> isCardWithCardNumberAndPinAvailable() {
        return (cardNumber, pin) -> CreditCardNumberValidator.isValid(cardNumber) && isCardWithNumberAndPinPresent(cardNumber, pin);
    }

    default void createCard() {
        Card card = CreditCardBuilder.init()
                .withCardNumber(CreditCardNumberGenerator.cardNumber())
                .withPin(PinGenerator.pin())
                .build();

        this.saveCard(card);

        MessageFactory messageFactory = new MessageFactory();
        System.out.println(messageFactory.get("CARD_INFORMATION_AFTER_CREATION_TEXT", card.getCreditCardNumber(), card.getPin()));
    }
}
