package banking.services;

import banking.generator.CreditCardGenerator;
import banking.models.Card;
import banking.repository.CardRepository;
import banking.util.CreditCardNumberValidator;

import java.util.Optional;
import java.util.function.Predicate;

import static banking.util.TextOutput.CARD_INFORMATION_AFTER_CREATION_TEXT;

public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final Predicate<String> isValid = CreditCardNumberValidator::validate;

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
    public boolean isCardAvailableByCardNumberAndPin(String cardNumber, String pin) {
        return isValid.test(cardNumber) && isCardByNumberAndPinPresent(cardNumber, pin);
    }

    @Override
    public boolean isCardByNumberPresent(String targetCardNumber) {
        return this.findCardByNumber(targetCardNumber).isPresent();
    }

    @Override
    public boolean isCardByNumberAndPinPresent(String cardNumber, String pin) {
        return repository.findCardByNumberAndPin(cardNumber, pin).isPresent();
    }

    @Override
    public Predicate<String> cardNumberPresentChecker() {
        return this::isCardByNumberPresent;
    }

    @Override
    public void createCard() {
        Card card = CreditCardGenerator.init()
                .createPin()
                .createCardNumber()
                .build();

        this.saveCard(card);

        System.out.printf(CARD_INFORMATION_AFTER_CREATION_TEXT,
                card.getCreditCardNumber(), card.getPin());
    }
}
