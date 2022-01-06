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
        long income = PromptUser.getAmountFromUser("\nEnter income:");
        cardService.updateBalanceByCardNumber(cardNumber, income);
        System.out.println("Income was added!\n");
    }

    @Override
    public void doTransfer() {
        String targetCardNumber;
        System.out.println("\nTransfer");
        targetCardNumber = PromptUser.getTargetCardNumberFromUser();

        if (!CardService.checkCreditCardNumber(targetCardNumber)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!\n");
        } else {
            if (targetCardNumber.equals(cardNumber)) {
                System.out.println("You can't transfer money to the same account!\n");
            } else {
                if (!Objects.equals(cardService.findByCardNumber(targetCardNumber).getCreditCardNumber(), targetCardNumber)) {
                    System.out.println("Such a card does not exist.\n");
                } else {
                    long amount = PromptUser.getAmountFromUser("Enter how much money you want to transfer:");
                    if (amount > cardService.readBalanceByCardNumber(cardNumber)) {
                        System.out.println("Not enough money!\n");
                    } else {
                        cardService.updateBalanceByCardNumber(cardNumber, targetCardNumber, amount);
                        System.out.println("Success!\n");
                    }
                }
            }
        }
    }

    @Override
    public void printAccountBalance() {
        System.out.println("\nBalance: " + cardService.readBalanceByCardNumber(cardNumber) +"\n");
    }

    @Override
    public void closeAccount() {
        cardService.deleteCard(cardNumber);
        System.out.println("\nThe account has been closed!\n");
    }
}
