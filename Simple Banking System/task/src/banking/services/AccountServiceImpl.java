package banking.services;

import banking.util.DisplayMessage;

import java.util.function.Predicate;

public class AccountServiceImpl implements AccountService {

    private final String cardNumber;
    private final CardService cardService;

    private final Predicate<String> isNotPresent;
    private final Predicate<String> isValid = CardService::isValid;

    public AccountServiceImpl(String cardNumber, CardService cardService) {
        this.cardNumber = cardNumber;
        this.cardService = cardService;
        isNotPresent = cardNumberPresentChecker().negate();
    }

    private Predicate<String> cardNumberPresentChecker() {
        return cardService::isCardByNumberPresent;
    }

    @Override
    public void addIncome() {
        long income = RequestUserTo.inputAmount(DisplayMessage.INCOME_INPUT_REQUEST_MSG);
        cardService.updateBalanceByCardNumber(cardNumber, income);
        System.out.println(DisplayMessage.INCOME_ADDED_TEXT);
    }

    @Override
    public void doTransfer() {
        String targetCardNumber;
        System.out.println(DisplayMessage.TRANSFER_TEXT);
        targetCardNumber = RequestUserTo.inputTargetCardNumber();

        if (isValid.negate().test(targetCardNumber)) {
            System.out.println(DisplayMessage.CARD_NUMBER_ERROR_MSG);
        } else {
            if (targetCardNumber.equals(cardNumber)) {
                System.out.println(DisplayMessage.SAME_ACCOUNT_ERROR_MSG);
            } else {
                if (isNotPresent.test(targetCardNumber)) {
                    System.out.println(DisplayMessage.CARD_NOT_EXISTS_ERROR_MSG);
                } else {
                    long amount = RequestUserTo.inputAmount(DisplayMessage.AMOUNT_TO_TRANSFER_INPUT_REQUEST_MSG);
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
        cardService.deleteCardByNumber(cardNumber);
        System.out.println(DisplayMessage.ACCOUNT_CLOSED_TEXT);
    }
}
