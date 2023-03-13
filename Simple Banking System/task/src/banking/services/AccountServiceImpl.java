package banking.services;

import banking.util.CreditCardNumberValidator;
import banking.util.PropertiesLoader;

import java.util.Properties;

import static banking.services.TransferResult.*;
import static banking.util.UserInput.inputAmount;
import static banking.util.UserInput.request;

public class AccountServiceImpl implements AccountService {

    private String cardNumber;
    private final CardService cardService;

    private final Properties properties;

    {
        properties = PropertiesLoader.getInstance().messages();
    }

    public AccountServiceImpl(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public boolean login() {
        this.cardNumber = request(properties.getProperty("USER_CARD_NUMBER_INPUT_REQUEST_MSG"));
        String pin = request(properties.getProperty("USER_PIN_INPUT_REQUEST_MSG"));
        return cardService.isCardWithCardNumberAndPinAvailable(cardNumber, pin);
    }


    @Override
    public void addIncome() {
        long income = inputAmount(properties.getProperty("INCOME_INPUT_REQUEST_MSG"));
        cardService.addIncome(cardNumber, income);
        log("INCOME_ADDED_TEXT");
    }

    @Override
    public void doTransfer() {
        System.out.println(properties.getProperty("TRANSFER_TEXT"));
        String targetCardNumber = request(properties.getProperty("TARGET_CARD_NUMBER_INPUT_REQUEST_MSG"));
        TransferResult result = getTransferResult(targetCardNumber);
        log(result.name().concat("_MSG"));
    }

    @Override
    public void printAccountBalance() {
        System.out.printf(properties.getProperty("ACCOUNT_BALANCE_TEXT") + "\n\n", cardService.findBalanceByCardNumber(cardNumber));
    }

    @Override
    public void closeAccount() {
        cardService.deleteCardByNumber(cardNumber);
        log("ACCOUNT_CLOSED_TEXT");
    }

    @Override
    public void createAccount() {
        cardService.createCard();
    }

    private TransferResult getTransferResult(String targetCardNumber) {
        TransferResult result;
        if (targetCardNumber.equals(cardNumber)) {
            return SAME_ACCOUNT_ERROR;
        } else if (!CreditCardNumberValidator.isValid(targetCardNumber)) {
            result = CARD_NUMBER_ERROR;
        } else if (!cardService.isCardWithNumberPresent(targetCardNumber)) {
            result = CARD_NOT_EXISTS_ERROR;
        } else {
            long amount = inputAmount(properties.getProperty("AMOUNT_TO_TRANSFER_INPUT_REQUEST_MSG"));
            result = cardService.transfer(amount, cardNumber, targetCardNumber);
        }
        return result;
    }

    private void log(String propertyKey) {
        System.out.println(properties.getProperty(propertyKey) + "\n");
    }
}
