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
    public TransferResult transfer(long amount, String from, String to) {
        if (to.equals(from)) {
            return SAME_ACCOUNT_ERROR;
        } else if (amount > findBalanceByCardNumber(from)) {
            return NOT_ENOUGH_MONEY_ERROR;
        } else {
            repository.transfer(amount, from, to);
            return SUCCESS;
        }
    }


    @Override
    public boolean isCardWithNumberAndPinPresent(String cardNumber, String pin) {
        return repository.findCardByNumberAndPin(cardNumber, pin).isPresent();
    }
}
