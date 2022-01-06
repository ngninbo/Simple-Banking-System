package banking.services;

import java.util.Scanner;

public interface PromptUser {


    /**
     * @param command Text message telling the user what to da, e.e. Enter amount
     * @return Value entered by the user
     */
    static long getAmountFromUser(String command) {
        System.out.println(command);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    /**
     * Prompt the user to enter the card number to which money has to be transfer
     * @return Target card number to which money has to be transfer
     */
    static String getTargetCardNumberFromUser() {
        String targetCardNumber;
        System.out.println("Enter card number:");
        Scanner scanner = new Scanner(System.in);
        targetCardNumber = scanner.nextLine();
        return targetCardNumber;
    }

    /**
     * Prompt users to enter their card number or PIN
     * @param command String message
     * @return User input
     */
    static String getCardInformationFromUser(String command) {
        String input;
        System.out.println(command);
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        return input;
    }

}
