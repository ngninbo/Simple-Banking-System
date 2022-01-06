package banking.services;

import banking.models.Card;
import banking.repository.CardRepository;

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
    public Card findByCardNumber(String cardNumber) {
        return repository.getCardByNumber(cardNumber);
    }

    @Override
    public long readBalanceByCardNumber(String cardNumber) {
        return repository.getBalance(cardNumber);
    }

    @Override
    public void updateBalanceByCardNumber(String cardNumber, long income) {
        repository.setBalance(cardNumber, income);
    }

    @Override
    public void deleteCard(String cardNumber) {
        repository.deleteCard(cardNumber);
    }

    @Override
    public void updateBalanceByCardNumber(String cardNumber, String targetCardNumber, long amount) {
        repository.setBalance(cardNumber, targetCardNumber, amount);
    }

    @Override
    public boolean validateCard(String cardNumber, String pin) {
        boolean isValid = false;
        if (CardService.checkCreditCardNumber(cardNumber)) {
            Card card = findByCardNumber(cardNumber);
            if (card != null) {
                if (card.getPin().equals(pin)) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }
}
