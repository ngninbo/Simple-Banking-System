package banking.services;

import banking.builder.CreditCardBuilder;
import banking.domain.MessageFactory;
import banking.generator.CreditCardNumberGenerator;
import banking.generator.PinGenerator;
import banking.models.Card;
import banking.util.CreditCardNumberValidator;

import java.util.function.BiPredicate;

import static banking.services.TransferResult.*;
import static banking.util.UserInput.inputAmount;
import static banking.util.UserInput.request;

public class AccountServiceImpl implements AccountService {

    private String cardNumber;
    private final CardService cardService;
    private final MessageFactory messageFactory = new MessageFactory();
    private final CreditCardNumberValidator validator = new CreditCardNumberValidator();

    public AccountServiceImpl(CardService cardService) {
        this.cardService = cardService;
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
        TransferResult result = getTransferResult(targetCardNumber);
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
        Card card = CreditCardBuilder.init()
                .withCardNumber(CreditCardNumberGenerator.cardNumber())
                .withPin(PinGenerator.pin())
                .build();

        cardService.saveCard(card);

        System.out.println(messageFactory.get("CARD_INFORMATION_AFTER_CREATION_TEXT", card.getCreditCardNumber(), card.getPin()));
    }

    private BiPredicate<String, String> isCardWithCardNumberAndPinAvailable() {
        return (cardNumber, pin) -> cardService.findCardByNumber(cardNumber)
                .map(card -> (pin.equals(card.getPin())))
                .orElse(Boolean.FALSE);
    }

    private TransferResult getTransferResult(String targetCardNumber) {
        TransferResult result;
        if (targetCardNumber.equals(cardNumber)) {
            return SAME_ACCOUNT_ERROR;
        } else if (!validator.setCreditCardNumber(targetCardNumber).validate()) {
            result = CARD_NUMBER_ERROR;
        } else if (cardService.findCardByNumber(targetCardNumber).isEmpty()) {
            result = CARD_NOT_EXISTS_ERROR;
        } else {
            long amount = inputAmount(messageFactory.from("AMOUNT_TO_TRANSFER_INPUT_REQUEST_MSG"));
            result = cardService.transfer(amount, cardNumber, targetCardNumber);
        }
        return result;
    }

    private void log(String propertyKey) {
        System.out.println(messageFactory.from(propertyKey));
    }
}
