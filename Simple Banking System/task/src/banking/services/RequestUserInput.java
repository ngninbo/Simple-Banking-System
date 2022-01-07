package banking.services;

import banking.util.DisplayMessage;

import java.util.Scanner;

public interface RequestUserInput {


    /**
     * @param command Text message telling the user what to da, e.e. Enter amount
     * @return Value entered by the user
     */
    static long requestAmountForTransaction(String command) {
        System.out.println(command);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    /**
     * Prompt the user to enter the card number to which money has to be transfer
     * @return Target card number to which money has to be transfer
     */
    static String requestTargetCardNumberForTransfer() {
        return requestCardInformation(DisplayMessage.TARGET_CARD_NUMBER_INPUT_REQUEST_MSG);
    }

    /**
     * Prompt users to enter their card number or PIN
     * @param command String message
     * @return Card number or PIN
     */
    static String requestCardInformation(String command) {
        String input;
        System.out.println(command);
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        return input;
    }

}
