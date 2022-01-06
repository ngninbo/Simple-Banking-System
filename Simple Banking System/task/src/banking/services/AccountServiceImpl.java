package banking.services;

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
        long income = PromptUser.enterAmount("\nEnter income:");
        cardService.addIncome(cardNumber, income);
        System.out.println("Income was added!\n");
    }

    @Override
    public void doTransfer() {
        String targetCardNumber;
        System.out.println("\nTransfer");
        targetCardNumber = PromptUser.inputTargetCardNumber();

        if (CardService.checkCreditCardNumber(targetCardNumber)) {
            if (!targetCardNumber.equals(cardNumber)) {
                if (Objects.equals(cardService.getCardByNumber(targetCardNumber).getCreditCardNumber(), targetCardNumber)) {
                    long amount = PromptUser.enterAmount("Enter how much money you want to transfer:");
                    if (amount <= cardService.getBalance(cardNumber)) {
                        cardService.setBalance(cardNumber, targetCardNumber, amount);
                        System.out.println("Success!\n");
                    } else {
                        System.out.println("Not enough money!\n");
                    }
                } else {
                    System.out.println("Such a card does not exist.\n");
                }
            } else {
                System.out.println("You can't transfer money to the same account!\n");
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again!\n");
        }
    }

    @Override
    public void printAccountBalance() {
        System.out.println("\nBalance: " + cardService.getBalance(cardNumber) +"\n");
    }

    @Override
    public void closeAccount() {
        cardService.deleteCard(cardNumber);
        System.out.println("\nThe account has been closed!\n");
    }
}
