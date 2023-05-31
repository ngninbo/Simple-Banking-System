package banking.services;

import banking.models.Card;
import banking.repository.CardRepository;

import java.util.Optional;

import static banking.services.TransferResult.*;

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
        return repository.findBalanceByCardNumber(cardNumber);
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
    public TransferResult transfer(long amount, String source, String target) {
        if (amount > findBalanceByCardNumber(source)) {
            return NOT_ENOUGH_MONEY_ERROR;
        } else {
            repository.transfer(amount, source, target);
            return SUCCESS;
        }
    }
}
