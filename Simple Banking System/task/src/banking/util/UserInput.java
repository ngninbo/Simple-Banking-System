package banking.util;

import java.util.Scanner;

public interface UserInput {

    /**
     * Reuest user to input an amount
     * @param command Text message telling the user what to da, e.e. Enter amount
     * @return amount {@link Long}
     */
    default long inputAmount(String command) {
        return Long.parseLong(request(command));
    }

    /**
     * Prompt users to enter something
     *
     * @param command String message
     * @return {@link String} user input
     */
    default String request(String command) {
        System.out.println(command);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
