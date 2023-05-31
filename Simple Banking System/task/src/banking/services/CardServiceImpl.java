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
    public long findBalanceByCardNumber(String cardNumber) {
        return repository.findCardByNumber(cardNumber).map(Card::getBalance).orElse(0L);
    }

    @Override
    public void addIncome(String cardNumber, long income) {
        repository.addIncome(cardNumber, income);
    }

    @Override
    public void deleteCardByNumber(String cardNumber) {
        repository.deleteCardByCardNumber(cardNumber);
    }

    @Override
    public void transfer(long amount, String source, String target) {
        repository.transfer(amount, source, target);
    }
}
