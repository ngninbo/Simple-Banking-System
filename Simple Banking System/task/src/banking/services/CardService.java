package banking.services;

import banking.builder.CreditCardBuilder;
import banking.generator.CreditCardNumberGenerator;
import banking.generator.PinGenerator;
import banking.models.Card;
import banking.util.CreditCardNumberValidator;
import banking.util.PropertiesLoader;

import java.util.Optional;
import java.util.function.Predicate;

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

    default boolean isCardWithCardNumberAndPinAvailable(String cardNumber, String pin) {
        return CreditCardNumberValidator.isValid(cardNumber) && isCardWithNumberAndPinPresent(cardNumber, pin);
    }

    default Predicate<String> isCardNumberPresent() {
        return this::isCardWithNumberPresent;
    }

    default void createCard() {
        Card card = CreditCardBuilder.init()
                .withCardNumber(CreditCardNumberGenerator.cardNumber())
                .withPin(PinGenerator.pin())
                .build();

        this.saveCard(card);

        System.out.printf(
                PropertiesLoader.getInstance().messages()
                        .getProperty("CARD_INFORMATION_AFTER_CREATION_TEXT") +"%n%n",
                card.getCreditCardNumber(),
                card.getPin());
    }
}
