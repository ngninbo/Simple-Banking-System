package banking.util;

import java.util.Scanner;

public interface RequestUserTo {


    /**
     * @param command Text message telling the user what to da, e.e. Enter amount
     * @return Value entered by the user
     */
    static long inputAmount(String command) {
        System.out.println(command);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    /**
     * Prompt the user to enter the card number to which money has to be transfer
     *
     * @return Target card number to which money has to be transfer
     */
    static String inputTargetCardNumber() {
        return inputCardInformation(PropertiesLoader.getInstance().properties()
                .getProperty("TARGET_CARD_NUMBER_INPUT_REQUEST_MSG"));
    }

    /**
     * Prompt users to enter their card number or PIN
     *
     * @param command String message
     * @return Card number or PIN
     */
    static String inputCardInformation(String command) {
        String input;
        System.out.println(command);
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        return input;
    }

}
