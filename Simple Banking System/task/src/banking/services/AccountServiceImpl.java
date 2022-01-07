package banking.services;

import banking.util.DisplayMessage;

import java.util.Objects;

public class AccountServiceImpl implements AccountService {

    private final String cardNumber;
    private final CardService cardService;

    public AccountServiceImpl(String cardNumber, CardService cardService) {
        this.cardNumber = cardNumber;
        this.cardService = cardService;
    }

    @Override
    public void addIncome() {
        long income = RequestUserInput.requestAmountForTransaction(DisplayMessage.INCOME_INPUT_REQUEST_MSG);
        cardService.updateBalanceByCardNumber(cardNumber, income);
        System.out.println(DisplayMessage.INCOME_ADDED_TEXT);
    }

    @Override
    public void doTransfer() {
        String targetCardNumber;
        System.out.println(DisplayMessage.TRANSFER_TEXT);
        targetCardNumber = RequestUserInput.requestTargetCardNumberForTransfer();

        if (!CardService.checkCreditCardNumber(targetCardNumber)) {
            System.out.println(DisplayMessage.CARD_NUMBER_ERROR_MSG);
        } else {
            if (targetCardNumber.equals(cardNumber)) {
                System.out.println(DisplayMessage.SAME_ACCOUNT_ERROR_MSG);
            } else {
                if (!Objects.equals(cardService.findByCardNumber(targetCardNumber).getCreditCardNumber(), targetCardNumber)) {
                    System.out.println(DisplayMessage.CARD_NOT_EXISTS_ERROR_MSG);
                } else {
                    long amount = RequestUserInput.requestAmountForTransaction(DisplayMessage.AMOUNT_TO_TRANSFER_INPUT_REQUEST_MSG);
                    if (amount > cardService.readBalanceByCardNumber(cardNumber)) {
                        System.out.println(DisplayMessage.NOT_ENOUGH_MONEY_ERROR_MSG);
                    } else {
                        cardService.updateBalanceByCardNumber(cardNumber, targetCardNumber, amount);
                        System.out.println(DisplayMessage.SUCCESS_MSG);
                    }
                }
            }
        }
    }

    @Override
    public void printAccountBalance() {
        System.out.printf(DisplayMessage.ACCOUNT_BALANCE_TEXT, cardService.readBalanceByCardNumber(cardNumber));
    }

    @Override
    public void closeAccount() {
        cardService.deleteCard(cardNumber);
        System.out.println(DisplayMessage.ACCOUNT_CLOSED_TEXT);
    }
}
