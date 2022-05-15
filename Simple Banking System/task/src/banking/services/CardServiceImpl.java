package banking.services;

import banking.builder.CreditCardBuilder;
import banking.models.Card;
import banking.repository.CardRepository;
import banking.util.CreditCardNumberValidator;
import banking.util.PropertiesLoader;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final Predicate<String> isValid = CreditCardNumberValidator::isValid;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveCard(Card card) {
        repository.saveCard(card);
    }

    @Override
    public Optional<Card> findCardByNumber(String cardNumber) {

        return repository.findCardByNumber(cardNumber);
    }

    @Override
    public long readBalanceByCardNumber(String cardNumber) {
        return repository.findCardByNumberAndReturnBalance(cardNumber);
    }

    @Override
    public void updateBalanceByCardNumber(String cardNumber, long income) {
        repository.updateCardByNumber(cardNumber, income);
    }

    @Override
    public void deleteCardByNumber(String cardNumber) {
        repository.deleteCardByCardNumber(cardNumber);
    }

    @Override
    public void updateBalanceByCardNumber(String cardNumber, String targetCardNumber, long amount) {
        repository.updateCardsByNumbers(cardNumber, targetCardNumber, amount);
    }

    @Override
    public boolean isCardWithCardNumberAndPinAvailable(String cardNumber, String pin) {
        return isValid.test(cardNumber) && isCardWithNumberAndPinPresent(cardNumber, pin);
    }

    @Override
    public boolean isCardWithNumberPresent(String targetCardNumber) {
        return this.findCardByNumber(targetCardNumber).isPresent();
    }

    @Override
    public boolean isCardWithNumberAndPinPresent(String cardNumber, String pin) {
        return repository.findCardByNumberAndPin(cardNumber, pin).isPresent();
    }

    @Override
    public Predicate<String> cardNumberPresentChecker() {
        return this::isCardWithNumberPresent;
    }

    @Override
    public void createCard() throws IOException {
        Card card = CreditCardBuilder.init()
                .withCardNumber()
                .withPin()
                .build();

        this.saveCard(card);

        System.out.printf(
                PropertiesLoader
                        .loadProperties("logs.properties")
                        .getProperty("CARD_INFORMATION_AFTER_CREATION_TEXT") +"%n%n",
                card.getCreditCardNumber(),
                card.getPin());
    }
}
