package banking.services;

import banking.models.Card;
import banking.repository.CardRepository;

import java.util.Optional;

public class CardServiceImpl implements CardService {

    private final CardRepository repository;

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
        return CardService.isValid(cardNumber) && isCardAvailableByCardNumberAndPin(cardNumber, pin);
    }

    @Override
    public boolean isCardByNumberPresent(String targetCardNumber) {
        return this.findCardByNumber(targetCardNumber).isPresent();
    }

    @Override
    public boolean isCardByNumberAndPinPresent(String cardNumber, String pin) {
        return repository.findCardByNumberAndPin(cardNumber, pin).isPresent();
    }
}
