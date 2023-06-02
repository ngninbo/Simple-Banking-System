package banking.services;

import banking.builder.CardCreator;
import banking.models.Card;

import java.util.function.BiPredicate;

import static banking.util.UserInput.inputAmount;
import static banking.util.UserInput.request;

public class AccountExecutorService extends TransferExecutor implements AccountService {

    private String cardNumber;

    public AccountExecutorService(CardService cardService) {
        super(cardService);
    }

    @Override
    public boolean login() {
        this.cardNumber = request(messageFactory.from("USER_CARD_NUMBER_INPUT_REQUEST_MSG"));
        String pin = request(messageFactory.from("USER_PIN_INPUT_REQUEST_MSG"));
        return validator.setCreditCardNumber(cardNumber).validate() && isCardWithCardNumberAndPinAvailable().test(cardNumber, pin);
    }


    @Override
    public void addIncome() {
        long income = inputAmount(messageFactory.from("INCOME_INPUT_REQUEST_MSG"));
        cardService.addIncome(cardNumber, income);
        log("INCOME_ADDED_TEXT");
    }

    @Override
    public void doTransfer() {
        System.out.println(messageFactory.from("TRANSFER_TEXT"));
        String targetCardNumber = request(messageFactory.from("TARGET_CARD_NUMBER_INPUT_REQUEST_MSG"));
        TransferResult result = doTransfer(cardNumber, targetCardNumber);
        log(result.name().concat("_MSG"));
    }

    @Override
    public void printAccountBalance() {
        System.out.println(messageFactory.get("ACCOUNT_BALANCE_TEXT", cardService.findBalanceByCardNumber(cardNumber)));
    }

    @Override
    public void closeAccount() {
        cardService.deleteCardByNumber(cardNumber);
        log("ACCOUNT_CLOSED_TEXT");
    }

    @Override
    public void createAccount() {
        Card card = new CardCreator().create();
        cardService.saveCard(card);

        System.out.println(messageFactory.get("CARD_INFORMATION_AFTER_CREATION_TEXT", card.getCreditCardNumber(), card.getPin()));
    }

    private BiPredicate<String, String> isCardWithCardNumberAndPinAvailable() {
        return (cardNumber, pin) -> cardService.findCardByNumber(cardNumber)
                .map(card -> (pin.equals(card.getPin())))
                .orElse(Boolean.FALSE);
    }

    private void log(String propertyKey) {
        System.out.println(messageFactory.from(propertyKey));
    }
}
