package banking.services;

import banking.util.CreditCardNumberValidator;

import java.util.function.Predicate;

import static banking.util.TextOutput.*;

public class AccountServiceImpl implements AccountService {

    private final String cardNumber;
    private final CardService cardService;

    private final Predicate<String> isNotPresent;
    private final Predicate<String> isValid = CreditCardNumberValidator::validate;

    public AccountServiceImpl(String cardNumber, CardService cardService) {
        this.cardNumber = cardNumber;
        this.cardService = cardService;
        this.isNotPresent = cardService.cardNumberPresentChecker().negate();
    }


    @Override
    public void addIncome() {
        long income = RequestUserTo.inputAmount(INCOME_INPUT_REQUEST_MSG);
        cardService.updateBalanceByCardNumber(cardNumber, income);
        System.out.println(INCOME_ADDED_TEXT);
    }

    @Override
    public void doTransfer() {
        String targetCardNumber;
        System.out.println(TRANSFER_TEXT);
        targetCardNumber = RequestUserTo.inputTargetCardNumber();

        if (isValid.negate().test(targetCardNumber)) {
            System.out.println(CARD_NUMBER_ERROR_MSG);
        } else {
            if (targetCardNumber.equals(cardNumber)) {
                System.out.println(SAME_ACCOUNT_ERROR_MSG);
            } else {
                if (isNotPresent.test(targetCardNumber)) {
                    System.out.println(CARD_NOT_EXISTS_ERROR_MSG);
                } else {
                    long amount = RequestUserTo.inputAmount(AMOUNT_TO_TRANSFER_INPUT_REQUEST_MSG);
                    if (amount > cardService.readBalanceByCardNumber(cardNumber)) {
                        System.out.println(NOT_ENOUGH_MONEY_ERROR_MSG);
                    } else {
                        cardService.updateBalanceByCardNumber(cardNumber, targetCardNumber, amount);
                        System.out.println(SUCCESS_MSG);
                    }
                }
            }
        }
    }

    @Override
    public void printAccountBalance() {
        System.out.printf(ACCOUNT_BALANCE_TEXT, cardService.readBalanceByCardNumber(cardNumber));
    }

    @Override
    public void closeAccount() {
        cardService.deleteCardByNumber(cardNumber);
        System.out.println(ACCOUNT_CLOSED_TEXT);
    }
}
